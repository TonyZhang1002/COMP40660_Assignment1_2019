# COMP40660 Assignment1

This is a java program which can calculate actual throughput and 10GB data transmitting time after users appoint the protocol, standard and transimitting data rate.

### Run

```
path/to/COMP40660_Assignment1_2019/out/artifacts/COMP40660_Assignment1_2019_jar
```

Run the `COMP40660_Assignment1_2019.jar` in this folder by using:

```
java -jar COMP40660_Assignment1_2019.jar
```

After that, pass the protocol, data rate, and standard one by one. (Use "a", "g", "n" as the way to represent different standard).

### The shortages of this model

There are three main shortages in my thoughts.

First of all is that this model does not consider the existence of "BO", where different clients might need to use this techniques to avoid colliding with each others. Therefore, the real world speed will be slower comparing to this model's results, especially when several (even up to 10) clients are using one single AP. However, because BO is more like a real world compromise, it is really hard to implement that in the codes. So, we would just leave it that way.

Second shortage is that this model consider the signal as consistent and none-fluctuant. However, in real world, signal might be affected by different factors, especially when wall or furnitures are very dense inside a certain area. In this kind of case, transimitting speed will be affected. But, in our model, we did not consider that.

The last shortage is that, in this model, we only take "udp" or "tcp" as the only protocol that transmit data. Nevertheless, a real network device often uses different protocols or ways to access internet while some combinations of approaches are often employed. For examples, a chat app might use udp package to transmitting live voice message while using tcp to transmit word messages. In this case, the actual scenario is not that ideal as our model sets.

In conclusion, theses are the three main shortages while there are also some minor shortages we did not consider in this model. For instances, does TCP ack message need rts or cts? Do we need to consider PCF, CFP, or CP in the model? Apart from that, this model did demonstrate the ideal transmitting speed and time, and could be used as a market tool for companies or buying reference for customers.

### Why the actual speeds might different from this model

The reason why the actual speeds might different from this model, as I mentioned in part two, is mainly because,  firstly, we did not employed the "BO" mechanisms, which is widely used in real world applications. Secondly, many factors in real world may affect the signal, like walls, rain drops, furnitures, or even people themselves. Thidly is that we did not take different protocals' combinations in count. One single transimitting protocol is setted. Other than that, in real world, some techniques like "Contention based Distributed Coordination Function" or "Point Coordination Function" may also be employed which will definitely effect the throughput.

### The benefits of Aggregation

Clearly we can see that by using aggregation, the network speed goes up sharply with more than 10X rate. For instance, in "TCP, 43.3MHz, 802.11ac_w1", withoud aggregation's throughput, in my calculation, is 22.795 Mbps. However, with aggregation, the speed will be 550.836 Mbps, which is up to 25X faster comparing to previous one. 

Let's take a look at why by using aggregation, the throughput will be much faster. In the previous model, sending CTS, RTS, ACK will cost several frames, which need many 4ns or 3.6ns. After all those frames being sent, we only transmit 1500 bytes data. The n/ac's advantage comparing to a/g is that their data bits per OFDM symbol is very high (25000 bits per symbol max in ac_w2 comparing to 216 in a). However, without aggregation, this advantages cannot be fully represented because there are only 1500 bytes data need to be transmitted in a loop. By using aggregation, thousands of bytes of data can be transimitted inside one loop, which can fully implement 802.11n and 802.11ac's advantage. And that is the reason why we can see that the speed will be much faster in this scenario.