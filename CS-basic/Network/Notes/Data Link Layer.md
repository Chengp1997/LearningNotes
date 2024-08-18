# [Data Link Layer](https://github.com/CyC2018/CS-Notes/blob/master/notes/%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C%20-%20%E9%93%BE%E8%B7%AF%E5%B1%82.md)

数据链路层 - 关注的是 **frame** -- 会将从上层传输过来的数据进行头尾的封装成frame

## Terms

- hosts/ routers - nodes ： 所有主机，路由器，都可以看作一个节点。data link layer中数据的交换就是发生在这些**相邻的物理节点**之间
- Links: 其中的link包括 
  - wired links 有线连接
  - wireless links 无线连接
  - LANs 局域网
- **MAC address**： 要在主机之间进行数据传输，就需要知道相邻主机的地址，封装在frame header中，
  - 特指链路层地址 - 6byte， 48bit， 12个字母- 6个十六进制数字组成。
  - 用来标识网络适配器（网卡）
  - 一台主机拥有多少个网络适配器就有多少个 MAC 地址。例如笔记本电脑普遍存在无线网络适配器和有线网络适配器，因此就有两个 MAC 地址。

**Data-link layer负责的就是这些相邻的link之间发生的数据传输**

## Services

- **framing**, link access: 将datagram 封装成 **frame** ，添加header, trailer(**header中包含了MAC地址，用来定位source和dest**)
- **reliable** delivery between adjacent nodes
- flow control, error detection, error correction(可以修正比特错误，不需要重新传输), half-duplex/full-duplex





## ARP

把