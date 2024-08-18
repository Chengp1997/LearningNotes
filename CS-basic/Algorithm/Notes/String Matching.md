# String Matching

Alphabet/set of characters

T：text

P：pattern

|T|=n

|P|=m

n>>m

## Straightforward algorithm

从左到右遍历一遍

从0位开始到（n-m+1）,每次都比较subString(i,i+m-1).

#### worst case:  (n-m)m--- O（nm）

​	根本不存在这个text中，需要比（n-m）次，并且对于每个subString自己需要比较m次

​	根本不存在这个text中，需要比（n-m）次，并且对于每个subString自己需要比较m次



## String Matching with Finite automata

![1555808539304](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1555808539304.png)

Then, for each node *k* and character *X* different from *P**k*, 

the arrow labelled *X* will go to the node *r* so that *r* is 

maximal with （从何处开始比）

![1555810487359](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1555810487359.png)、

#### Best    O(n) 

#### Worst  O(ma)

​	对于m长度的pattern，有a种可能，要比这么多次

​	space O（ma）

## KMP   

Boyer-Moore  和 KMP相比，前者更快，但是后者逻辑更简单。

！！！

![1555994111681](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1555994111681.png)



> *j* gives the current position in the text
>
> *k* gives the current position in the pattern 
>
> **We look at the** ***k*****-1 characters already matched**
>
> -  **-if k=1 (no characters matched) restart matching at** 
>
>    **position j+1 in the text and position 1 in the pattern**
>
> - **if  some non-empty maximal prefix of P of length** 
>
>    ***r*****-1 occurs at the end of these** ***k*****-1 characters of P** 
>
>    **then restart the matching** 
>
>    **on** **position** ***r*** **in the pattern and** 
>
>    **on** **position** ***j*** **in the text**
>
> * **if there is no prefix that occurs at the end of these** 
>
>    **k-1 characters of P,**
>
>    **then restart the matching on position** ***j*** **in the text** 
>
>    **and on position 1 in the pattern**   

![1555994857167](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1555994857167.png)![1555994863892](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1555994863892.png)

#### worst case: n+m (线性的)

特点在于，它会告诉你从哪里继续比？

有限自动机在于，它每次都是从mismatch 的地方开始从新比对

#### 优点

不需要回去比较匹配

能够边接收信息边匹配

最差也有O(m+n)

fail数组能够先算完了再来用

