#!/bin/bash

COUNT=0;



for i in $(seq 0 4)
do
  echo "Executing test.. $i";
  cat "IN/in$i.txt" | java -jar target/tut10-1.0-SNAPSHOT-jar-with-dependencies.jar > "OUT/out$i.txt";
done
