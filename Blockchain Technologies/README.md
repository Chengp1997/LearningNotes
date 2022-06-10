# Environment

Solidity Version: 0.8.4

Platform: REMIX

Test network: Rospen test network

Wallet application: MetaMask



## File Structure

our executable code is file is `carBidApplication.sol`

carBidApplication

--- Car_Repo_Contract

--- Car_Bid_Contract



## Introduction 

It's a used car information collection and bidding application.

Car_Repo_Contract is used for managing the cars and calculate the estimated price of the used car for bidding. The car agent can manage the cars being bid using this contract. It contains a list of cars that are available for sale.

Car_Bid_Contract is used for buyers to bid for the car. When a buyer selects a car and offers the price, this contract is responsible for determining whether the bid is valid or not, whether the bid time is still available, etc.

### Architecture

<img src="https://lh6.googleusercontent.com/oQpOUsFdEJlbNENTlv5G_zfRjnpwB0wExSjZqfsV5v8dMOlif-bYKfZZP6hEF-JbwHyXpP20PHKwycGty9gzMCtDShtgIqUQQ2iJ3BS5jF1qxw1ER2tv_2Uc8_tGIcIF5qkQb5J9Ao_NnzuJ7Lfyzw" alt="img" style="zoom: 33%;" />

### Features

- Car_Repo_Contract
  - Add cars
  - Change car's owner
  - calculate estimated price for bidding
  - upload accident record
  - Get Information
    - get curent owner
    - Get accident history
- Car_Bid_Contract
  - set auction time
  - bid
  - withdraw
  - aution end
    - Update onwer
    - Transfer coins

### Run

You can use our code and run on the remix online platform directly. 

