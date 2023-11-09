import org.apache.storm.lambda.SerializableBiConsumer;
import org.apache.storm.lambda.SerializableConsumer;
import org.apache.storm.topology.*;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

/*
All processing in topologies is done in bolts.
 Bolts can do anything from filtering, functions, aggregations, joins, talking to databases, and more.


 */
public class yfBolt extends BaseBasicBolt {
    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        String symbol = input.getValue(0).toString();
        String timestamp =  input.getString(1);

        Double price = (Double) input.getValueByField("price");
        Double prevClose = input.getDoubleByField("prev_close");

        Boolean gain = true;

        if (price<=prevClose) {
            gain = false;
        }

        collector.emit(new Values(symbol, timestamp, price,gain));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("company", "timestamp", "price","gain"));

    }
}
