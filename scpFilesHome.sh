(
    cd ./src

    for f in *.java 
    do
        scp $f pi@swiftbot00188:~/dev/assignment3/
    done
)
