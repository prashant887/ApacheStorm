Instance Spout+Boult=Task 

Cluster 
1. Master (Nimbus): Managed by ZK
2. Worker : (Supervisor ) , spawns exectors 

Data Exchange B/W Tasks , Data FLow is managed my Stream Group 
1 Spout + 3 Boult = 4 Tasks
Stream Grouping : Staregy of deciding data from Spout goes to which Boult 
Default is Shuffle Staregery Radomly one Boult is picked 
Choose Boult based on field , Field Routing Stratergy 
Data going to all Boult , All grouping Stratergy 
