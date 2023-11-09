import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;

public class TopologyMain {
    public static void main(String[] args) throws Exception {

        //Build Topology
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("Yahoo-Finance-Spout", new yfSpout()); //A spout is a source of streams in a topology
        builder.setBolt("Yahoo-Finance-Bolt", new yfBolt()) //All processing in topologies is done in bolts
                .shuffleGrouping("Yahoo-Finance-Spout");//Connect Bolt to Spout

        StormTopology topology = builder.createTopology();
        //Configuration
        Config conf = new Config();
        conf.setDebug(true);
        conf.put("fileToWrite", "data/yfOutput/output.txt");

        //Submit Topology to cluster
        /*
        try{
            StormSubmitter.submitTopology("MyTopology", conf, topology);
        }catch (Exception e){
            e.printStackTrace();
        }

         */

        LocalCluster cluster=new LocalCluster();
try {
    cluster.submitTopology("Stock-Tracker-Topology",conf,topology);
    Thread.sleep(10000);
}
finally {
    cluster.shutdown();
}
    }


}
