// SPDX-License-Identifier: GPL-3.0
pragma solidity ^0.8.4;


/**
 * @title Transaction_contract
 * @dev Store & retrieve value in a variable
 * @custom:dev-run-script ./scripts/deploy_with_ethers.ts
 */
contract Car_Repo_Contract {
    
    struct Car {
        string car_id;
        uint init_price;
        uint[] accident_costs_history; 
        address current_owner;
        uint miles;
        uint car_age;
        uint tractions_times; //owner_history;
    }

    // <car_id, Car>
    mapping (string => Car) public cars;

    // change car owner
    function change_owner(string calldata cid, address newOwner) public {
        cars[cid].current_owner = newOwner;
        // change owner times
        cars[cid].tractions_times++;
    }

    function add_cars(string calldata car_id, uint init_price, address current_owner, 
    uint miles, uint car_age, uint tractions_times) public {
        uint[] memory accident_costs_history;
        Car memory a = Car(car_id, init_price, accident_costs_history, current_owner, miles, car_age, tractions_times); // 
        cars[car_id] = a;
    }

    //
    function calculate_price(string calldata car_id) public view returns (uint) { 
        uint age_cost_unit = 500;
        uint256 miles_cost_unit = 10;

        uint tot_dis = 0;

        uint size = cars[car_id].accident_costs_history.length;
        for(uint i = 0; i < size; ++i){
            uint accident = cars[car_id].accident_costs_history[i];
            tot_dis+= accident;
        }

        uint age_cost = cars[car_id].car_age * age_cost_unit;
        uint mils_cost = cars[car_id].miles * miles_cost_unit;
        tot_dis+= age_cost + mils_cost;

        uint ans = cars[car_id].init_price - tot_dis;
        return ans;

    }

    Car var_car;
    //accident_h
    function upload_accident_record(string calldata car_id,  uint accident_cost) public {
        Car memory car = cars[car_id];
        var_car = car;
        var_car.accident_costs_history.push(accident_cost);
        cars[car_id] = var_car;
    }

    function get_current_owner(string calldata car_id) public view returns (address){
        return cars[car_id].current_owner;
    }

    function get_AccidentHistory(string calldata car_id) public view returns (uint[] memory){
        return cars[car_id].accident_costs_history;
    }

}



/********************************************************************************************/

// 


contract Car_Bid_Contract {
    // Parameters of the auction. Times are either
    // absolute unix timestamps (seconds since 1970-01-01)
    // or time periods in seconds.
    address payable public beneficiary;
    uint public auctionEndTime;

    Car_Repo_Contract car_repo_contrast = Car_Repo_Contract(0xd2a5bC10698FD955D1Fe6cb468a17809A08fd005);

    struct Trade_car {
        string t_car_id;
        uint tprice;
        address current_owner;
    }


    Trade_car public car;
    // Car_Repo_Contract car_repo_contrast = Car_Repo_Contract(0x4FefCD41EAc7f67a3572B17bA33C7D9E9F2D5405);
    uint public estimated_price;

    function set_trade_car(string memory t_car_id) public {
        uint tprice = car_repo_contrast.calculate_price(t_car_id);
        address current_owner = car_repo_contrast.get_current_owner(t_car_id);
        car.t_car_id = t_car_id;
        car.tprice = tprice;
        car.current_owner = current_owner;
    }

    // Current state of the auction.
    address public highestBidder;
    uint public highestBid;

    // Allowed withdrawals of previous bids
    mapping(address => uint) public pendingReturns;

    // Set to true at the end, disallows any change.
    // By default initialized to `false`.
    bool ended;

    // Events that will be emitted on changes.
    event HighestBidIncreased(address bidder, uint amount);
    event AuctionEnded(address winner, uint amount);

    // Errors that describe failures.

    // The triple-slash comments are so-called natspec
    // comments. They will be shown when the user
    // is asked to confirm a transaction or
    // when an error is displayed.

    /// The auction has already ended.
    error AuctionAlreadyEnded();
    /// There is already a higher or equal bid.
    error BidNotHighEnough(uint highestBid);
    /// The auction has not ended yet.
    error AuctionNotYetEnded();
    /// The function auctionEnd has already been called.
    error AuctionEndAlreadyCalled();

    /// Create a simple auction with `biddingTime`
    /// seconds bidding time on behalf of the
    /// beneficiary address `beneficiaryAddress`.
    constructor(
        uint biddingTime,
        // address payable beneficiaryAddress
        string memory trade_car_id
    ) {

        auctionEndTime = block.timestamp + biddingTime;

        set_trade_car(trade_car_id);      
        beneficiary = payable(car.current_owner);
        estimated_price = car_repo_contrast.calculate_price(trade_car_id);  
    }

 

    /// Bid on the auction with the value sent
    /// together with this transaction.
    /// The value will only be refunded if the
    /// auction is not won.
    function bid() external payable {
        // No arguments are necessary, all
        // information is already part of
        // the transaction. The keyword payable
        // is required for the function to
        // be able to receive Ether.

        // Revert the call if the bidding
        // period is over.
        if (block.timestamp > auctionEndTime)
            revert AuctionAlreadyEnded();

        // If the bid is not higher, send the
        // money back (the revert statement
        // will revert all changes in this
        // function execution including
        // it having received the money).
        if (msg.value <= highestBid)
            revert BidNotHighEnough(highestBid);
        
        // ....
        if (msg.value <= car.tprice)
            revert BidNotHighEnough(highestBid);

        if (highestBid != 0) {
            // Sending back the money by simply using
            // highestBidder.send(highestBid) is a security risk
            // because it could execute an untrusted contract.
            // It is always safer to let the recipients
            // withdraw their money themselves.
            pendingReturns[highestBidder] += highestBid;
        }
        highestBidder = msg.sender;
        highestBid = msg.value;
        emit HighestBidIncreased(msg.sender, msg.value);
    }

    /// Withdraw a bid that was overbid.
    function withdraw() external returns (bool) {
        uint amount = pendingReturns[msg.sender];
        if (amount > 0) {
            // It is important to set this to zero because the recipient
            // can call this function again as part of the receiving call
            // before `send` returns.
            pendingReturns[msg.sender] = 0;

            // msg.sender is not of type `address payable` and must be
            // explicitly converted using `payable(msg.sender)` in order
            // use the member function `send()`.
            if (!payable(msg.sender).send(amount)) { // pay back to the bidder
                // No need to call throw here, just reset the amount owing
                pendingReturns[msg.sender] = amount;
                return false;
            }
        }
        return true;
    }

    /// End the auction and send the highest bid
    /// to the beneficiary.
    function auctionEnd() external {
        // It is a good guideline to structure functions that interact
        // with other contracts (i.e. they call functions or send Ether)
        // into three phases:
        // 1. checking conditions
        // 2. performing actions (potentially changing conditions)
        // 3. interacting with other contracts
        // If these phases are mixed up, the other contract could call
        // back into the current contract and modify the state or cause
        // effects (ether payout) to be performed multiple times.
        // If functions called internally include interaction with external
        // contracts, they also have to be considered interaction with
        // external contracts.

        // 1. Conditions
        if (block.timestamp < auctionEndTime)
            revert AuctionNotYetEnded();
        if (ended)
            revert AuctionEndAlreadyCalled();

        // 2. Effects
        ended = true;
        emit AuctionEnded(highestBidder, highestBid);

        // 3. Interaction
        beneficiary.transfer(highestBid);
        //.....

        car_repo_contrast.change_owner(car.t_car_id,highestBidder);
    }
}
// }

