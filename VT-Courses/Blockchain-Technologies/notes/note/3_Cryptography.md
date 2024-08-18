# Cryptography

## Definition

### What is

The study of techniques for **secure communications** in the presence of **adversaries**. 数学上的一种方式（一种加密通话的方式）

> Security is not the same as cryptography.

#### Cryptographic algorithms

are designed around **computational hardness assumptions** 基于一些难以计算的assumptions

- 现在大部分的情况：adversary is computationally bounded-- problem cannot be solved efficiently

**Example**

- Hash functions: MD5, SHA-1, SHA-256

#### Information- theoretically secure schemes

很难

- No computational assumptions about the adversary
- 难以breakble

Example : one-time pad (using a new random secret key for each encryption

<img src="/Users/chengeping/Documents/LearningMaterial/VTNote/CS5594 blocchain Technologies/notePicture/one-time-pad.png" alt="image-20220209175910886" style="zoom:50%;" />

**Problems**

- Conditions for one-time pad to be theoretically unbreakable
  - key必须和要加密的paintext一样长
  - 

- Problems in practical adoption

#### Role

1. 可以达成信息安全的各种目标

- Confidentiality: keeping sensitive information private(对数据进行加密-解密)
- Integrity: keeping information unmodified(**hash function**)
- Authentication: verifying someone/something is who/ what it is declared to be 保证是某个人的操作，例如：
  - 电子签名/证书
  - sth you are: biometric(指纹，face ID)
  - sth you have: one-time token
  - sth you know: PIN,password
  - multi factor(MFA): password+cell phone
- Non-repudiation: can not deny having perfomed a particular action

2. cryptographic primitives：于是就有了crptography的雏形--low level cryptographic algorithms，需要一些算法来保证系统的安全性（SHA-256,AES,RSA,PRG,digital signatures）

#### CIA Triad

系统安全设计的终极目标

- Confidentiality: 保证信息private
- Integrity: 
- Availability: 

Using CIA Triad to analyze cyber security

Cryptography **has nothing related t**o security !

### Why

- Bitcoin is crypto currency(加密货币)
- Crypto i**s a mandatory building block** in Bitcoin/blockchain
  - Cryptographic hash functions and hash-based primitives
  - Public key cryptography and primities

**Blockchain is based on distributed system and cryptography**



## Cryptographic Hash Functions

### Hash Function

a function H with two basic properties

- Compression: H maps input x of arbitrary length to an output y=H(x) of a fixed length 把任意长度的信息，通过Hash function ，转换成fixed 长度的信息
  - proces- hashing data 哈希方法有时候又被叫做 compression functions/ one-way functions
  - Outputs- hash values/message digest
- Ease of computation 

<img src="/Users/chengeping/Documents/LearningMaterial/VTNote/CS5594 blocchain Technologies/notePicture/hash function.png" alt="image-20220209182857423" style="zoom:50%;" />

以下也是hash function的一些重要的性质，只是很多时候不是所有都有

- Preimage resistance
  - computationally hard to reverse the hash function 能加密，但是很难reverse获得input。(防止黑客获得input)
- 2nd preimage resistance
  - Give an input and its hash, computationally hard to find a different input with the same hash：简而言之，不同的input，几乎不会获得一个和已经存在的hash值相同的hash值(防止黑客替换input)
  - <img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220209183414699.png" alt="image-20220209183414699" style="zoom:50%;" />
- Collision resistance 可以说是上面的properties的加强版本。
  - **computationally hard** to find two different inputs that result in the same hash(有可能，但是几率非常小)任意两个不同的input，不会获得相同的值
  - <img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220209183448059.png" alt="image-20220209183448059" style="zoom:50%;" />

#### Relationships between properties

Does collision  resistance imply 2nd preimage resistance? **Yes**

2nd preimage resistance- collision resistance ? No

preimage resistance imply 2nd? No

……

总结- **只有 collision resistance imply 2nd preimage resistance**

##### Building Collision-Resistant hash Functions

- Merkle-Damgard(MD) Construction

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220214175151014.png" alt="image-20220214175151014" style="zoom:50%;" />



#### Popular hash functions

**MD5** (Message Digest)

- Output 128-bit
- Broken!
- Too weak to be used for critical applicaitons

**SHA-1**

- Output-160-bit
- Broken!

SHA-2 family :**SHA-256/**

BLAKE-256/512 good for embedded devices

#### Applications

- Password storage for websites
  - Server logon processes store hash of the password(<user_id,H(p)>)
  - <img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220214180504789.png" alt="image-20220214180504789" style="zoom:50%;" />
- Data file integrity check using checksum
  - Detect any changes made to a data file
  - <img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220214180841157.png" alt="image-20220214180841157" style="zoom:50%;" />

#### Birthday Paradox

Why 160 bits(SHA-1)? Or 256 bits(SHA-256) in the output of a hash function

- if too long: unneccessary overhead
- if too short: loss of strong collision resistance property--vulnerable to **birthday attack**

**Birthday paradox**

23个人中有相同生日的概率超过50%，30个人中，超过70%，366中，100%能找到相同的。如下计算，

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220214181546126.png" alt="image-20220214181546126" style="zoom:50%;" />

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220214181748715.png" alt="image-20220214181748715" style="zoom:50%;" />

**Birthday Attack**

A **cryptographic attck** that exploits the birthday paradox to find collisions for a hash function. 

对于输出长度为m的hash function有

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220214182456462.png" alt="image-20220214182456462" style="zoom:50%;" />

注意conclusion！总结可知，160-256最合适

#### Salted Hashing

- deteministic hashing is not sufficient for storing passwords
  - vulnerable to rainbow table attack
    - 一种 pre-computation attack
    - Rainbow table: pre-computed tables of hashes and their plaintext passwords
    - Using less processing time and more space than brute-force attack
      - basic brute-force
      - dictionary attack

- salted hashing
  - Salt: a **random unique token** passed to the hash function along with the password plaintext to generate a unique hashed password
  - Goal: make it infeasible to construct pre-computed databases to crack password hashes
    - 对每一种salt，难以生成rainbow tables
  - salt必须unique per password
  - salt can be stored right next to the salted and hashed password in the database
    - 需要password和salt才能validate a password.
    - Salts are not assumed to be secret
  - 为了安全，除了salt，还能使用second piece of salt和salt的方式一样，但是更安全了

#### Keyed Hashing

Collisions are impossible to eliminate completely不可能完全消除的！

- keyed hash functions
  - 使用cryptographic key 和 cryptographic hash function 来产生authentication code---keyed and hashed
  - HMAC
    - 用来检测attacker 是否 tampered with a message(cryptographic checksums)
- Comparison to salted hashing
  - salt并不是secret，但是key是
  - salts are supposed to vary，但是key是实体之间公用的

#### Encryption a good hash function?



## Hash-Based Primitives

- Authentication
  - digital signature
  - Hash-based massage autentication code(HMAC)
  - authenticated data structures
    - Hash chain
    - merkle tree
- Proof of work(PoW)

### Digital Signature

一种cryptographic 机制，用来verify **authenticity** and **integrity** of digital data

- **Cryptographic hash functions+public key cryptography(PKC)**
- Hash-then-sign paradigm
  - 先用hashing缩短任意长度的信息 d=H(M) **--hash function**
  - 然后使用signer's private key sign the digest s=Sign(d) **--PKC**
- 使用public key来verify

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220216174843394.png" alt="image-20220216174843394" style="zoom:50%;" />

- 一些信息
  - 信息不同，digital signature不同。
  - 公钥和私钥都是sender生成的，但是只有public是共享的
  - 私钥需要好好保存。
  - 产生digital signature， hashing并不是必须的， 只是比较长而已（blockchain perfe hashing， 因为生成的东西是定长，效率更高）
  - digital signatrue和encryption是不同的
    - encryption
      - public key 写，private key read
      - provides confidentiality
    - signing
      - private key 写，public key verify
      - provides message authentication and non repudiation
  - Digital signature 提供了：
    - Data integrity: 只要有一点点更改， 都会导致签名不同
    - authenticity：can use public key to confirm the signature
    - Non-repudiation: 
- security aspects
  - relying on collision resistece property 
  - attacks on **MD5, SHA-1** threaten current signatures 会威胁到这个
  - well-worth the attacker's effort
    - one collision---forgery for any signer
  - Improvement 



### HMAC

Hash-based message authentication code

- 使用 keyed hash function
- 保证了**integrity** 和 **authenticity**
- simple key-prepend construction(MAC)
  - procedure
  - 然而！length extention attacks会导致失效，于是有了HMAC
    - 
- improved construction(HMAC)
  - 2passes of hash computation ![image-20220216180308160](/Users/chengeping/Library/Application Support/typora-user-images/image-20220216180308160.png)
- MAC/HMAC does not encrypt the message
- magical number!!

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220216180654460.png" alt="image-20220216180654460" style="zoom:50%;" />

### Hash Chain

**Definition**: a successive application of a cryptographic hash function H to x

![image-20220216180937345](/Users/chengeping/Library/Application Support/typora-user-images/image-20220216180937345.png)

![image-20220216181010048](/Users/chengeping/Library/Application Support/typora-user-images/image-20220216181010048.png)

**One-time password**

Idea: 生成一串密码，一次用一个

优点

缺点：

存储消耗过大

applicaiton ：



**S/key one- time pasword protocol**

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220216181407646.png" alt="image-20220216181407646" style="zoom:50%;" />

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220216181633157.png" alt="image-20220216181633157" style="zoom:50%;" />

**chained hash**

- more general construction than one-way hash chians

- useful for authenticating a sequence of data values

- H* authenticates the entire chain

  <img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220216181910903.png" alt="image-20220216181910903" style="zoom:50%;" />

HashList---list of hash value : 可以把巨大的数据分成n blocks，每个blocks都可以有自己的hash value。

### HashList

- Definition: a list of hashes of data blocks in a file

例如: a subtree of merkle hash tree

top/root hash: commitment of the entire hash list

- Protecting data integrity
  - verification is complete after checking the root hash
  - better than a simple hash of the entire file: only need to redownload the damaged blocks



### Merkle Hash Tree

**二叉树**

- 每一片叶子都是：cryptographic hash of data block
- 每一个内部节点都是child nodes的cryptographic
- top/root hash： cmomitment of the entire merkle tree
- generalization of hash chain and hash list
- 能够efficient and secure verification 地保存内容
- 在叶子结点进行hashing，能够防止信息泄漏

![image-20220216182628275](/Users/chengeping/Library/Application Support/typora-user-images/image-20220216182628275.png)

- Authentication of the root is necessary to use the tree
- merkle tree operations== updating insertion ,dletion

#### Merkle hash tree in blockchain

前面可以存储一些其他信息，例如时间啊等。

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220216183823389.png" alt="image-20220216183823389" style="zoom:50%;" />

## Public Key Cryptography

#### Public Key(Asymmetric) Cryptography

- cryptographic operations use different keys

- known as asymmetric key cryptography, public key cryptography 

- Asymmetric encryption:

  ![image-20220221175441948](/Users/chengeping/Library/Application Support/typora-user-images/image-20220221175441948.png)

- Digital signatures

-  key management

computational encryption: Asymmetric/public key encryption

- 2 keys
  - private 私有的
  - public 大家共享
  - idea for **Confidentiality** encrypt- public; decrypty -private

**Requirements**

- must be computationally easy to encrypt or decrypt a message given the appropriate key
- computationally infeasible to derive the private key from the public key
- computationally infeasible to determine the private key from a ciphertext

PKC relies on some known mathematical hard problems

- Large integer factorization(RSA)
- Discrete logarithmic(DSA)

- Elliptic curve discrete logarithmic Digital Signature(ECDSA)

### Public Key Primities(RSA)

Rivest-Shamir-adleman

- Most popular public key method
  - provide both public key encryption and digital signature
- 由很多因素决定，系数很高，很难被解码
- Variable key length(2048 bits or bigger)
- varaible plaintext block size:
  - plaintext block size must be smaller than key size
  - ciphertext block size is same as key size

#### RSA algorithm

1. Euler's totient function $\phi(n)$

- number of positive integers less than n and relatively prime to n
  - relatively prime means **having no facotrs in common with n** 没有因数？

If m and n are relatively prime, then $\phi(mn)=\phi(m)\phi(n)$

Example:

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220221181935660.png" alt="image-20220221181935660" style="zoom:50%;" />

2. computingpublic/privatekeys	

- find large primes p and q(using miller-rabin primality test)
- let n=p*q, then $\phi(n)=(p-1)(q-1)$
- choose e<n such that e is ==**relatively prime**== to $\phi(n)$
- compute d such that ed=1 mod $\phi(n)$
  - d is ==multiplicative inverse== of e under modulo $\phi(n)$
- Public key(e,n)
- private key d



<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220221183348621.png" alt="image-20220221183348621" style="zoom:50%;" />

<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220221183446079.png" alt="image-20220221183446079" style="zoom:50%;" />





<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220221183900917.png" alt="image-20220221183900917" style="zoom:50%;" />



<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220221184110102.png" alt="image-20220221184110102" style="zoom:50%;" />

![image-20220221184520442](/Users/chengeping/Library/Application Support/typora-user-images/image-20220221184520442.png)

### Digital signature(DSA)

### Elliptic Curve Cryptography(ECDSA)















