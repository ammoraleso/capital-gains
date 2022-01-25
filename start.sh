case $1 in
  "clean")
    echo "Cleaning up..."
    mvn clean
    ;;

  "install")
    echo "Installing dependencies..."
    mvn install
    ;;

  "compile")
      echo "Compiling dependencies..."
      mvn compile
      ;;

  "run")
    echo "Run the application... (Clean, install, compile, run)"
    mvn clean install compile exec:java
    ;;
esac