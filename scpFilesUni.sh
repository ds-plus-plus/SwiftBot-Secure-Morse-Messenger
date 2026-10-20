(
    cd ./src

    for f in *.java 
    do
        scp $f swiftbot:~/dev/assignment3/
    done
)
