import javax.swing.*;

public class Action extends JFrame {
    private JPanel MainPanel;
    private JProgressBar springProgressBar;
    private JButton Compress;
    private JButton Release;
    private JTextField PowerField;
    private JTextField koField;
    private JLabel LengthLabel;
    private JLabel TimeLabel;
    private JSlider TimeSlider;

    public void setPower(String power) throws NumberFormatException {
        this.power = Float.parseFloat(power);
    }
    public void setKo(String ko) throws NumberFormatException {
        this.ko = Float.parseFloat(ko);
    }

    private Spring spring;
    private boolean isActive;
    private float power,ko;

    //Enabling components on JForm
    private void Change(boolean check){
        Compress.setEnabled(check);
        Release.setEnabled(!check);
        PowerField.setEnabled(check);
        koField.setEnabled(check);
        TimeSlider.setEnabled(check);
    }

    double time;

    //constructor
    public Action(){
        super("Spring");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(MainPanel);
        this.pack();
        this.setVisible(true);
        Release.setEnabled(false);
        springProgressBar.setMaximum(80);
        springProgressBar.setValue(50);

        //runnable method for thread
        Runnable task = () -> {
            time = 0;
            while(isActive) {
                long start = System.nanoTime();
                int k = TimeSlider.getValue();
                int pos = this.spring.Move(time * k/(50*1000));
                this.springProgressBar.setValue(pos);
                this.TimeLabel.setText("Time: " + (int) time/(1000) + " sec.");
                this.LengthLabel.setText("Spring length: " + pos + " m.");
                try {
                    double raz = (System.nanoTime()- start)*Math.pow(10,-6);
                    Thread.sleep(5);
                    time += 5 - raz;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        //Button action listener for compress the spring
        Compress.addActionListener(e -> {
            isActive = false;
            Change(isActive);
            try {
                setPower(PowerField.getText());
                setKo(koField.getText());

                spring = new Spring(power, ko);
                springProgressBar.setValue(spring.Move(0));
            } catch(NumberFormatException numberFormatException) {
                Change(!isActive);
                JOptionPane.showMessageDialog(MainPanel, "Enter the parameters correctly!");
            }catch (IllegalArgumentException illegalArgumentException){
                Change(!isActive);
                JOptionPane.showMessageDialog(MainPanel,illegalArgumentException.getMessage());
            }
        });

        //Button action listener for release the spring
        Release.addActionListener(e -> {
            isActive = true;
            Change(isActive);
            new Thread(task).start();
        });
    }
    //just main, where we create a form
    public static void main(String[] args) {
        new Action();
    }
}