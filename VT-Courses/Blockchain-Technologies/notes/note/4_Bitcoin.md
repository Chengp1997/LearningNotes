# Bitcoin

## Intro

**Not equivalent to blockchain**

是一种，在分布式系统上运行的，使用区块链技术的电子货币

## Bitcoin Components

P2P network

Address and wallet

Transactions

Blocks

consensus

Mining

## Bitcoin Network

![image-20220314180951605](/Users/chengeping/Library/Application Support/typora-user-images/image-20220314180951605.png)

![image-20220314181226435](/Users/chengeping/Library/Application Support/typora-user-images/image-20220314181226435.png)

点对点，所有节点都是相同的，任意节点可以随时加入或者离开，如果节点3小时后没响应，则

**Transaction Propagation**

![image-20220314181841639](/Users/chengeping/Library/Application Support/typora-user-images/image-20220314181841639.png)

如果遇到冲突时：

![image-20220314182047775](/Users/chengeping/Library/Application Support/typora-user-images/image-20220314182047775.png)

**Two types of nodes**

- Full nodes

  ![image-20220314182658027](/Users/chengeping/Library/Application Support/typora-user-images/image-20220314182658027.png)

- Thin/SPV nodes

  ![image-20220314182710392](/Users/chengeping/Library/Application Support/typora-user-images/image-20220314182710392.png)

  

## Address and Wallet

### Address

Bitcoin address: **Hash of public key**

为了获得一个transaction，需要获得：**signing key; address**, some info

**Pseudonymity**：main goal of Bitcoin(Public-key cryptography)

![image-20220314183300848](/Users/chengeping/Library/Application Support/typora-user-images/image-20220314183300848.png)

### Wallet 

**Manage your private key**

3 main goals

- Availability: 可以花
- Security：其他人无法碰
- Convenience：管理简单

![image-20220314184405427](/Users/chengeping/Library/Application Support/typora-user-images/image-20220314184405427.png)

![image-20220316175914565](/Users/chengeping/Library/Application Support/typora-user-images/image-20220316175914565.png)



## Bitcoin Transaction

![image-20220316183717105](/Users/chengeping/Library/Application Support/typora-user-images/image-20220316183717105.png)

![image-20220316183823227](/Users/chengeping/Library/Application Support/typora-user-images/image-20220316183823227.png)

![image-20220316182108962](/Users/chengeping/Library/Application Support/typora-user-images/image-20220316182108962.png)

![image-20220316182124251](/Users/chengeping/Library/Application Support/typora-user-images/image-20220316182124251.png)



关注是否total input=total ouput，不需要知道之前的。

![image-20220316182510031](/Users/chengeping/Library/Application Support/typora-user-images/image-20220316182510031.png)

![image-20220321174755964](/Users/chengeping/Library/Application Support/typora-user-images/image-20220321174755964.png)

Bitcoin script



## Bitcoin Block

A block contains multiple transactions



## Consensus Mechanism



## Mining

