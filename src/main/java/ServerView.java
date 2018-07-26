import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by Krzysztof on 2018-07-24.
 */
public class ServerView extends JFrame{

    static int heigh=300;
    static int wight=300;

    // Elements of Panel
    private JPanel jPanel;
    private JButton serverOnOffBtn;
    private JTextArea inputView;
    private JLabel serverStatus;
    private JButton helpBtn;
    private JTextArea outputView;


    public ServerView()
    {
         this.setVisible(true);
         this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
         this.setSize(heigh,wight);

         addPanels();
    }



    private void addPanels()
    {
        this.add(jPanel);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public void addActionListner(ActionListener actionListener)
    {
        serverOnOffBtn.addActionListener(actionListener);
    }

   public void setInputView(String text)
   {

       inputView.setText(text);
   }

   public void setOutputView(String text)
   {
        outputView.setText(text);
   }

   public void setServerStatus(boolean isReady)
   {
       if(isReady) {

           serverStatus.setForeground(Color.GREEN);
       }
       else
       {
           serverStatus.setForeground(Color.RED);
       }
   }

}
