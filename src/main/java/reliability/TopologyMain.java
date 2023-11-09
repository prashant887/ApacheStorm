package reliability;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.thrift.TException;
import org.apache.storm.topology.TopologyBuilder;

public class TopologyMain {

    public static void main(String[] args) throws Exception {
        //Topology definition

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("Reliable-Spout", new ReliableWordReader());
        builder.setBolt("Random-Failure-Bolt",
                new randomFailureBolt()).shuffleGrouping("Reliable-Spout");

        //Configuration
        Config conf = new Config();
        conf.setDebug(true);
        conf.put("fileToRead", "data/sample.txt");

        //Topology run

        LocalCluster cluster = new LocalCluster();
        try{cluster.submitTopology("Random-Fail-Topology", conf, builder.createTopology());
            Thread.sleep(2000);} catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally{
            cluster.shutdown();}
    }

}

