all:
	echo "Requires a second argument: mfu | fifo | lru"

mfu:
	javac Main.java
	java Main

fifo:
	javac Paging/FIFO.java
	java -cp Paging/ FIFO

lru:
	javac Paging/LRU_Counter.java
	java -cp Paging/ LRU_Counter
