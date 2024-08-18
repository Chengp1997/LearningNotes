# Ethereum

- universal, programmable blockchain
  - **Account-based** transaction model
  - smart contract

## Crypto

- **ECDSA** for digital signatures(like Bitcoin)
- **Keccak-256** for hash functions(Bitcoin: SHA-256)

## Ethereum BLock

- 以太坊使用的是**账户/余额模式(Account balance)**，不是UTXO(Bitcoin)

- Block
  - Header: 15 elements
  - Body 
    - List of transactions
    - list of embers
- **State machine** 以太坊可以看成是一个状态机，transaction更改状态。

![image-20220330175735966](/Users/chengeping/Library/Application Support/typora-user-images/image-20220330175735966.png)

### Header

![image-20220330180351895](/Users/chengeping/Library/Application Support/typora-user-images/image-20220330180351895.png)

- 3 Merkle trees
  - transactions
  - Receipts
  - states

## Modified Merkle Patricia Trie(MPT)

以太坊中，用来存储的一种数据结构。

原因：以太坊是account-based，因此需要一种数据结构来进行搜索。

partricia+merkle tree

**Patricia trie**

也是 prefix tree。一棵字典树。

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220330181120385.png" alt="image-20220330181120385" style="zoom:50%;" />

**Merkle tree**

Hash tree

详细见以前的笔记。



**MPT是二者的结合**

![image-20220330181635824](/Users/chengeping/Library/Application Support/typora-user-images/image-20220330181635824.png)

## ommer list

**Goal:** provide small reward dfor miners when duplicate block solutions are found 会记录ommer，为了incentive

原因：很多时候，第一个被发现的solution才会加入链中。其他valid solution就会在链外。

Ommer: 

![image-20220330182638612](/Users/chengeping/Library/Application Support/typora-user-images/image-20220330182638612.png)

### uncles reward

为了激励 创造出 ommer的用户

根据 uncle block 的fresh程度来进行计算的

![image-20220330183253305](/Users/chengeping/Library/Application Support/typora-user-images/image-20220330183253305.png)

### uncle inclusion reward

![image-20220330183247129](/Users/chengeping/Library/Application Support/typora-user-images/image-20220330183247129.png)

## Denomination

版本不同，价格不同

![image-20220330183745904](/Users/chengeping/Library/Application Support/typora-user-images/image-20220330183745904.png)

## BItcoin VS Ethereum

![image-20220330183901901](/Users/chengeping/Library/Application Support/typora-user-images/image-20220330183901901.png)

