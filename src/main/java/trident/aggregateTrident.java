package trident;


import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.builtin.Count;
import org.apache.storm.trident.operation.builtin.Debug;
import org.apache.storm.tuple.Fields;
import wordCounter.WordReader;

public class aggregateTrident {

    public static void main(String[] args) throws Exception {

        TridentTopology topology = new TridentTopology();
        topology.newStream("lines", new WordReader())
                .each(new Fields("word"),
                        new splitFunction(),
                        new Fields("word_split"))
                .aggregate(new Count(), new Fields("count"))
                .each(new Fields("count"), new Debug())
        ;

        Config conf = new Config();
        conf.setDebug(true);
        conf.put("fileToRead", "data/sample.txt");

        if (args.length != 0 && args[0].equals("remote")) {
            StormSubmitter.submitTopology("Trident-Topology", conf, topology.build());
        } else {
            LocalCluster cluster = new LocalCluster();
            try {
                cluster.submitTopology("Trident-Topology", conf, topology.build());
                Thread.sleep(6000);
            } finally {
                cluster.shutdown();
            }

        }
    }
}
