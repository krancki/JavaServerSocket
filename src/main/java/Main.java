import com.sun.corba.se.spi.activation.Server;

/**
 * Created by Krzysztof on 2018-07-24.
 */
public class Main {

    public static void main(String[] args) {


        ServerView serverView = new ServerView();
        ServerModel serverModel = new ServerModel(serverView);

        ServerController serverController = new ServerController(serverModel,serverView);



    }
}
