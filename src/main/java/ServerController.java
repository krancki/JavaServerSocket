import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Krzysztof on 2018-07-24.
 */
public class ServerController {

    ServerModel serverModel;
    ServerView serverView;

    boolean serverStatus=false;

    public ServerController(ServerModel serverModel, ServerView serverView)
    {

        this.serverModel=serverModel;
        this.serverView=serverView;

        configurateView();
    }

    private void configurateView()
    {


        serverView.addActionListner(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(!serverStatus)
                {
                    serverModel.setServerOn();
                    serverStatus=true;
                    System.out.println("Server On");
                }else
                {
                    serverModel.setServerOff();
                    serverStatus=false;
                    System.out.println("Server off");
                }

            }
        });

    }


}
