package wordCounter;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.thrift.TException;
import org.apache.storm.topology.TopologyBuilder;

public class TopologyMain {

    public static void main(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("word-reader", new WordReader());
        builder.setBolt("word-counter", new WordCounter(),2)
                .shuffleGrouping("word-reader");


        Config conf = new Config();
        conf.put("fileToRead", "data/sample.txt");
        conf.put("dirToWrite", "data/wordCountShuffleOutput/");
        conf.setDebug(true);

        LocalCluster cluster = new LocalCluster();
        try{
            cluster.submitTopology("WordCounter-Topology", conf, builder.createTopology());
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            cluster.shutdown();
        }
    }
}
