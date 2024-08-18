# Web Service

[https://www.cnblogs.com/xdp-gacl/p/4048937.html](https://www.cnblogs.com/xdp-gacl/p/4048937.html)   有关web service请参考

> 从表面上看，Web Service就是一个应用程序向外界暴露出一个能通过Web进行调用的API，也就是说能用编程的方法通过 Web来调用这个应用程序。我们把调用这个Web Service的应用程序叫做客户端，而把提供这个Web Service的应用程序叫做服务端。
>
> 从深层次 看，Web Service是建立可互操作的分布式应用程序的新平台，是一个平台，是一套标准。它定义了应用程序如何在Web上实现互操作性，你可以用任何 你喜欢的语言，在任何你喜欢的平台上写Web service ，只要我们可以通过Web service标准对这些服务进行查询和访问。 

因此，由于大家的代码不尽相同，web service平台必须提供一套标准来保证大家的互相调用。

重要技术：

- XML+XSD：存储数据，存储方式
- SOAP：提供调用webservice的方法，发送请求的格式
- WSDL：描述Web服务的XML语言，提供了一种向其他程序描述Web服务的统一方法
- UDDI：创建可搜索的web服务注册中心

### XML

> WebService采用HTTP协议传输数据，采用XML格式封装数据
>
> XML解决了数据表示的问题，XML Schema(XSD)就是专门解决这个问题的一套标准。它定义了一套标准的数据类型，并给出了一种语言来扩展这套数据类型。

### SOAP（Simple Object Access Protocol）

> WebService通过HTTP协议发送请求和接收结果时，发送的请求内容和结果内容都采用XML格式封装，并增加了一些特定的HTTP消息头，以说明 HTTP消息的内容格式，这些特定的HTTP消息头和XML内容格式就是SOAP协议。SOAP提供了标准的RPC方法来调用Web Service。
>
> 或者说：**SOAP协议 = HTTP协议 + XML数据格式**



> 产生原因：SOAP 提供了一种标准的方法，使得运行在不同的操作系统并使用不同的技术和编程语言的应用程序可以互相进行通信。
>
> RPC 会产生兼容性以及安全问题；防火墙和代理服务器通常会阻止此类流量。
>
> 通过 HTTP 在应用程序间通信是更好的方法，因为 HTTP 得到了所有的因特网浏览器及服务器的支持。SOAP 就是被创造出来完成这个任务的。

- 基于XML和HTTP
- SOAP消息传递模型

![1543303265261](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1543303265261.png)

其中，对于SOAP Node![1543303287383](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1543303287383.png)

- SOAP协议格式
  - Request![1543304988422](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1543304988422.png)
  - Response![1543305260589](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1543305260589.png)
- SOAP Message
  - 包含XML”envelope“元素（**必须**）
  - 包含SOAP Header（**可选**）
  - 包含SOAP Body（”payload“）（**必须**）
  - 不能包含DTD引用
  - 不能包含XML处理指令
  - Structure:![1543305706156](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1543305706156.png)
- SOAP Example
  - SOAP必须使用Envelope 元素，它可以把XML文档定义为SOAP消息
  - SOAP 消息必须拥有与命名空间 "http://www.w3.org/2001/12/soap-envelope" 相关联的一个 Envelope 元素。

```xml
< ?xml version="1.0" encoding="UTF-8" ?> 
<env:Envelope 
     xmlns:env="http://www.w3.org/2001/09/soap-envelope"> 
    <env:Header>
        <n:alertcontrol                                 			xmlns:n="http://example.org/alertcontrol">
            <n:priority>1</n:priority>
            <n:expires>2001-06-22T14:00:00-		 05:00</n:expires>
        </n:alertcontrol>
    </env:Header>
    <env:Body>
        <m:alert xmlns:m="http://example.org/alert">
            <m:msg>Pick up Mary at school at 2pm</m:msg>
        </m:alert>
    </env:Body>
</env:Envelope>
```

- SOAP Header
  - 多有Header元素的直接子元素必须是合格的命名空间

  - SOAP在默认的命名空间中，("http://www.w3.org/2001/12/soap-envelope") 定义了三个属性。

    - mustUnderstand 属性

      - 可用于标识标题项对于要对其进行处理的接收者来说是强制的还是可选的。
      - `soap:mustUnderstand="0|1"`

    - actor 属性

      - 可被用于将 Header 元素寻址到一个特定的端点。

      - `soap:actor="URI"`

      - > can be either: none, next, ultimateReceiver. 
        >
        > ‘None’ is used to propagate information that does not need to be processed.
        >
        >  ‘Next’ indicates that a node receiving the message can process that block. 
        >
        > ‘ultimateReceiver’ indicates that the header is intended for the final recipient of the message 

    - encodingStyle 属性

      - 用于定义在文档中使用的数据类型。此属性可出现在任何 SOAP 元素中，并会被应用到元素的内容及元素的所有子元素上。SOAP 消息没有默认的编码方式。
      - `soap:encodingStyle="URI"`

  - 除此之外，还有一些如Transaction等其他源自信息

- SOAP Body

  - The body is intended for the application **specific data contained in the message** 包含打算传送到消息最终端点的实际 SOAP 消息。

  - 带RPC的远程方法调用

    ```XML
    <!--某远程方法-->
    public Float getQuote (String symbol); 
    
    <s:Envelope xmlns:s="http://www.w3.org/2001/06/soapenvelope">
        <s:Header>
            <m:transaction xmlns:m="soap-transaction" s:mustUnderstand="true">
                <transactionID>1234</transactionID>
            </m:transaction>
        </s:Header>
        <!--如下为body部分，包含的为实际传递的soap消息。注意，下面的n:getQuote部分并非SOAP标准的一部分-->
        <s:Body>
                <!--如下，即为RPC调用格式的SOAP-->
            <n:getQuote xmlns:n="urn:QuoteService">
                <symbol xsi:type="xsd:string">
                    IBM
                </symbol>
            </n:getQuote>
        
        </s:Body>
    </s:Envelope>
    ```

  - SOAP Response

    ```XML
    <s:Envelope xmlns:s="http://www.w3.org/2001/06/soap-envelope">
        <s:Body>
            <!--如下，即为RPC调用格式的SOAP的response-->
            <n:getQuoteResponse 			   		 
                 xmnls:n="urn:QuoteService">
                <value xsi:type=“xsd:float”> 
                    98.06
                </value> 
            </n:getQuoteResponse> 
            
        </s:Body> 
    </s:Envelope>
    ```

  - SOAP Faults

    ```XML
    <s:Envelope xmlns:s="http://www.w3.org/2001/06/soap-envelope">
        <s:Body> 
            <s:Fault>         
                <faultcode><!--识别故障的代码-->
                    Client.Authentication
                </faultcode> 
                <faultstring>
                    <!--可供人阅读的有关故障的说明-->
                    Invalid credentials
                </faultstring> 
                <faultactor><!--有关是谁引发故障的信息-->
                    http://acme.com/
                </faultactor> 
                <details> 
                  <!-- application specific details-->
                </details> 
            </s:Fault> 
        </s:Body> 
    </s:Envelope>
    ```

    - Standard SOAP Fault Codes用于描述醋无的faultcode
      - Version Mismatch无效命名空间
      - MustUnderstand: specify which header blocks were not understood
      - Server: error can’t be directly linked to the processing of the message
      - Client: there is a problem in the message (e.g. invalid authentication credentials)

- SOAP 数据封装
  - 不限制加码样式，常见的有：
  - Literal style: no coding rules, the message formats are negotiated by the SOAP sender and receiver.
  - Soap style: according to the soap coding style.



### WSDL（Web Service Description Language）

> 所以，WebService务器端首先要通过一个WSDL文件来说明自己家里有啥服务可以对外调用，服务是什么（服务中有哪些方法，方法接受 的参数是什么，返回值是什么），服务的网络地址用哪个url地址表示，服务通过什么方式来调用。
>
> 一个基于XML的语言，用于描述Web Service及其函数、参数和返回值。
>
> WebService服务提供商可以通过两种方式来暴露它的WSDL文件地址：1.注册到UDDI服务器，以便被人查找；2.直接告诉给客户端调用者。

WSDL定义了：这个服务是什么（interface），如何调用（how），在哪里调用（where）

![1543311839324](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1543311839324.png)

如图，requester要么直接从服务端获得服务，要么从registry发现服务。服务要先在UDDI服务端注册从而让人能够查找

- 一种XML文档
- Framework![1543410135695](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1543410135695.png)
- Elements of Abstract Part
    - Each portType is a set of operations in logic;
    - Each operation defines its message, e.g. input, output, to interact with other web services or agents
    - message is a set of datatypes in logic.
- Elements of Concrete Part 
    - binding describes **the rules** of the portType
      - Protocol of message transmitting, Network protocol, Coding rule of the message… 
    - A port only d**escribes one binding**, its URI describes the accessing address. 
    - ports which have the same accessing address are grouped into a service.


![1543416737371](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1543416737371.png)

- 标签

- | 元素       | 定义                       |
  | ---------- | -------------------------- |
  | <portType> | web service 执行的操作     |
  | <message>  | web service 使用的消息     |
  | <types>    | web service 使用的数据类型 |
  | <binding>  | web service 使用的通信协议 |

- 常见格式

  ```xml
  <definitions>
  
  <types>
    data type definitions........
  </types>
  
  <message>
    definition of the data being communicated....
  </message>
  
  <portType>
    set of operations......
  </portType>
  
  <binding>
    protocol and data format specification....
  </binding>
  
  </definitions> 
  ```

- <types>: 定义web service使用的数据类型

  - 使用XML schema， DTD或者其他类型
  - ![1543420656818](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1543420656818.png)

- <message>: 定义使用的消息

  - 定义方法的数据内容，可以理解为方法的参数
  - 每一个消息可能未input或者output的内容
  - ![1543420746173](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1543420746173.png)

- <portType>: （最重要）定义执行的操作

  - 定义了：web service，**能被执行的operation**，包**含的message**

  - <port>: 定义了web service的连接点，或者说<portType>的一个实体！！

    - 可以看成一个library![1543420912034](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1543420912034.png)

  - ![1543420925467](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1543420925467.png)

  - opeartion（4types）

    - one -way：receive， no response（email）有来无去

      - ![1543460552768](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1543460552768.png)

      - ```xml
        <message name="newTermValues">
            <part name="term" type="xs:string" /> 
            <part name="value" type="xs:string" /> 
        </message> 
        <portType name="glossaryTerms">
            <operation name="setTerm"> 
                
                <input name="newTerm" message="newTermValues" />
            
            </operation> 
        </portType > 
        ```

    - request-response：receive request， return response，有来有去

      - ![1543460569318](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1543460569318.png)

      - ```xml
        <message name="getTermRequest">
            <part name="term" type="xs:string"/> 
        </message> 
        <message name="getTermResponse"> 
            <part name="value"type="xs:string"/> 
        </message> 
        <portType name="glossaryTerms"> 
           <operation name="getTerm"> 
                
             <input message="getTermRequest"/> 
             <output message="getTermResponse"/> 
                
            </operation> 
        </portType> 
        ```

    - solicit-response：send a request, wait for a response，有去等回

      - ![1543465866696](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1543465866696.png)

    - notification: send a message, no response，有去无回

      - ![1543465887717](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1543465887717.png)

  - <bingding>: 定义使用的通信协议，信息是如何传递的，service在哪里
    - 两种变量
      - name: bingding的名称
      - type：port type
    - 4种方式
      - SOAP
      - HTTP GET
      - HTTP POST
      - MIME
    - 一个WSDL文档种可指定多种绑定形式
    - ![1543467679522](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1543467679522.png)
    - ![1543467846026](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1543467846026.png)
    - ![1543468099368](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1543468099368.png)
    - ![1543468108662](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1543468108662.png)

- 每个port与bingding中定义的绑定形式向对应![1543468296832](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1543468296832.png) 

- Service是ports的集合 

  ```xml
  <service name="StockQuoteService"> 
      <documentation> My first service </documentation> 
      <port name="StockQuotePort" binding="tns:StockQuoteBinding"> 
          <soap:address location="http://example.com/stockquote"/> 
      </port> 
  </service> 
  ```

  ![1543470229995](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1543470229995.png)





### UDDI（Universal Description Discovery and Integration）

可以理解为WSDL的门面

> UDDI 是一个独立于平台的框架，用于通过使用 Internet 来描述服务，发现企业，并对企业服务进行集成。
>
> - UDDI 指的是通用描述、发现与集成服务
> - UDDI 是一种用于存储有关 web services 的信息的目录。
> - UDDI 是一种由 WSDL 描述的 web services 界面的目录。
> - UDDI 经由 SOAP 进行通信

当一个服务想被他人发现时，要么1）主动向他人提供服务2）在UDDI上注册服务，等待别人发现（即提供ip地址？）

 This documentation has three aspects:

- basic information
- categorization
- technical data

UDDI扮演的角色

![1543480435615](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1543480435615.png)

简单描述：client先向UDDI询问服务；UDDI返回WSDL文档描述其提供的服务；client查找WSDL文档有关服务的信息；webservice返回对应的描述；client使用SOAP发出请求；webservice返回有关的服务



- UDDI data model![1543480605451](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1543480605451.png)
  - white pages: 服务提供者信息（名称，地址……）
  - yellow pages: 根据标准对服务的分类
  - green pages: 技术信息，如何使用每项服务
- ![1543480641348](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1543480641348.png)
  - businessEntity：对提供服务者的描述。每个实体都有key
    - schema![1543547270467](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1543547270467.png)
  - businessService：list，businessEntity提供的所有服务
    - schema![1543547363145](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1543547363145.png)![1543553734755](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1543553734755.png)
  - bindingTemplate：提供服务的技术描述，对某一服务的具体描述
    - schema![1543554049742](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1543554049742.png)![1543555562555](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1543555562555.png)
  - tModel：用来存储关于服务的其他信息，比如如何使用，使用条件等等，例如，使用的接口，协议
    - schema![1543556017694](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1543556017694.png)![1543556090994](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1543556090994.png)
  - publishAssertion：表述businessEntity之间的关系
- 总结来说：![1543556465415](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1543556465415.png)



UDDI拥有众多类型的interface

- Inquiry：寻找UDDI的entry
- publication：公布，修改UDDI注册
- security：access control
- subscription：subscribe 同changes
- replication：how to perform replication of information across nodes
- custody&ownership transfer: 更改拥有人，

WSDL 和UDDI可以相互映射![1543565246881](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1543565246881.png)![1543565802220](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1543565802220.png)



### UDDI&SOAP&WSDL

![1543556841112](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1543556841112.png)

简单来说

SOAP为请求以及回应的方式

WSDL定义了服务的内容

使用服务者通过发现UDDI来了解服务