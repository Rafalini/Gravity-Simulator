import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ChangeListener;

public class View extends JFrame
{
    MapPanel Space;
    public static int MapWidth = 1000;
    public static int MapHeight = 1000;
    JLabel FPSLabel, MainLabel, TimeLabel, MassLabel, XvelLabel, YvelLabel, PresetLabel, PresetSliderLabel, StarTimeLabel, GeneralInfo;
    JSlider TimeSlider, MassSlider, PresetSlider, StarTimeSlider;
    JTextField TimeField, MassField, XvelField, YvelField, PresetField, StarLiveTimeField;
    JTextArea LiveLog;
    JComboBox PlanetPreset, VelPreset, ObjPreset, MassPreset;
    JButton PresetButton, TimePlusButton, TimeMinusButton, ResetButton;
    JRadioButton AllDetails, SomeDetails, TurnOff;
    ButtonGroup LiveLogOptions;
    JCheckBox TracksCheck, MoreLinesCheck;

    public View()
    {
        Space = new MapPanel(this);
        this.setSize(MapWidth, MapHeight);
        this.setTitle("Okno na wszechświat");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(Space, BorderLayout.CENTER);
        this.setLocation(0,0);
        this.setResizable(true);
        this.setVisible(true);

        FPSLabel = new JLabel("FPSy:    Obiektów:    Mnożnik Czasu:", JLabel.CENTER);
        FPSLabel.setForeground(Color.YELLOW);
        JPanel tmp = new JPanel();
        tmp.setBackground(Color.BLACK);
        tmp.add(FPSLabel);
        this.add(tmp,BorderLayout.NORTH);

        JFrame MenuFrame = new JFrame();
        MenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MenuFrame.setTitle("Centrum dowodzenia wszechświatem");
        MenuFrame.setLocation(1000,0);
        MenuFrame.setSize(500,650);
        MenuFrame.setResizable(true);

        JPanel MenuPanel = new JPanel();

        GeneralInfo = new JLabel("<html><p width=\"400\">" +"Ustawienia poniżej dotyczą tworzonych obiektów kosmicznych."+
                " Po ustawieniu wybranej konfiguracji należy wybrać miejsce w kosmosie kursorem, a po kliknięciu pojawi się żądany obiekt."
                +" Można też ustawić z góry dobrany schemat rozmieszczenia, aby uruchomić symulację należy wybrać nie zerową wartość"
                +" na suwaku czasu."+"</p></html>\\");


        Box GlobalBox = Box.createVerticalBox();
        Box TimeBox  = Box.createHorizontalBox();
        Box MassBox  = Box.createHorizontalBox();
        Box ObjPresetBox = Box.createHorizontalBox();
        Box VelBox = Box.createHorizontalBox();
        Box PlanetsPresetBox = Box.createHorizontalBox();
        Box SettingsPresetBox = Box.createHorizontalBox();

        TimeLabel = new JLabel("Suwak czasu: ");
        TimeLabel.setToolTipText("Zmienia tempo upływu czasu, 0 = pauza");

        TimeSlider = new JSlider(0, 100, 0);
        TimeSlider.setMinorTickSpacing(5);
        TimeSlider.setMajorTickSpacing(20);
        TimeSlider.setPaintTicks(true);

        TimeField = new JTextField("",6);
        TimeField.setText(Integer.toString(TimeSlider.getValue()));                          //zmiana teksu na JTextField

        TimePlusButton = new JButton("Czas++");

        TimeBox.add(TimeLabel);
        TimeBox.add(TimeSlider);
        TimeBox.add(Box.createHorizontalStrut(5));
        TimeBox.add(TimeField);
        TimeBox.add(Box.createHorizontalStrut(20));
        TimeBox.add(TimePlusButton);

        MassLabel = new JLabel("Suwak masy: ");                                                           //GUI masy
        MassLabel.setToolTipText("Zmienia masę tworzonej planety: ");

        MassSlider = new JSlider(1, 40000, 2635);
        MassSlider.setMinorTickSpacing(1000);
        MassSlider.setMajorTickSpacing(5000);
        MassSlider.setPaintTicks(true);

        MassField = new JTextField("",6);
        MassField.setText("7562");                          //zmiana teksu na JTextField

        TimeMinusButton = new JButton(" Czas-- ");

        MassBox.add(MassLabel);
        MassBox.add(Box.createHorizontalStrut(5));
        MassBox.add(MassSlider);
        MassBox.add(Box.createHorizontalStrut(3));
        MassBox.add(MassField);
        MassBox.add(Box.createHorizontalStrut(20));
        MassBox.add(TimeMinusButton);

        XvelField = new JTextField("6", 5);                                                      //GUI prędkości
        YvelField = new JTextField("-13", 5);
        XvelLabel = new JLabel("Początkowy wektor prędkości planety   X: ");
        XvelLabel.setToolTipText("Ustawia początową prędkość planety, X rośnie w prawo, Y rośnie w dół");
        YvelLabel = new JLabel("   Y: ");

        VelBox.add(XvelLabel);
        VelBox.add(XvelField);
        VelBox.add(YvelLabel);
        VelBox.add(YvelField);

        StarTimeLabel = new JLabel("Suwak czasu życia gwiazdy");                                        //GUI gwiazdy
        StarTimeLabel.setToolTipText("Określa czas [sekundy] istnienia danej gwiazdy, po jego upływie gwiazda wybucha");
        StarTimeSlider = new JSlider(0,100, 30);
        StarTimeSlider.setMinorTickSpacing(5);
        StarTimeSlider.setMajorTickSpacing(20);
        StarTimeSlider.setPaintTicks(true);
        StarLiveTimeField = new JTextField("30",4);
        String [] objToCreate = {"Planeta","Gwiazda"};
        ObjPreset = new JComboBox(objToCreate);

        StarTimeSlider.setEnabled(false);
        StarLiveTimeField.setEnabled(false);

        ObjPresetBox.add(StarTimeLabel);
        ObjPresetBox.add(StarTimeSlider);
        ObjPresetBox.add(StarLiveTimeField);
        ObjPresetBox.add(ObjPreset);

        JPanel LiveLogPanel = new JPanel();                                                                 //GUI live logu
        Border LiveLogBorder = BorderFactory.createTitledBorder("Ilość informacji w logu");
        LiveLogPanel.setBorder(LiveLogBorder);
        LiveLogOptions = new ButtonGroup();
        AllDetails = new JRadioButton("Pełne");
        SomeDetails = new JRadioButton("Ważne");
        TurnOff = new JRadioButton("Brak");
        LiveLogOptions.add(AllDetails);
        LiveLogOptions.add(SomeDetails);
        LiveLogOptions.add(TurnOff);
        LiveLogPanel.add(AllDetails);
        LiveLogPanel.add(SomeDetails);
        LiveLogPanel.add(TurnOff);

        TracksCheck = new JCheckBox("Ścieżki planet");
        MoreLinesCheck = new JCheckBox("inne kreski");
        LiveLogPanel.add(TracksCheck);
        LiveLogPanel.add(MoreLinesCheck);
        AllDetails.setSelected(true);

        LiveLog = new JTextArea(10,30);
        LiveLog.setText("LiveLog activated!\n");
        LiveLog.setLineWrap(true);
        LiveLog.setWrapStyleWord(true);
        JScrollPane scrollbar1 = new JScrollPane(LiveLog, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        String [] setupoptions = {"Siatka","okręgi", "Spirala", "Fireworks"};                                                 //GUI schematów do wygenerowania
        PlanetPreset = new JComboBox(setupoptions);
        String [] velsetupoptions = {"Prędkość kołowa", "Prędkość losowa", "Brak prędkości"};
        VelPreset = new JComboBox(velsetupoptions);
        String [] masssetupoptions = {"Masa stała", "Masa losowa"};
        MassPreset = new JComboBox(masssetupoptions);

        JPanel PresetPanel = new JPanel();
        PresetLabel = new JLabel("<html><p width=\"100\">" +"Generowanie gotowych układów"+"</p></html>\\");
        PresetLabel.setToolTipText("<html><p width=\"400\">" + "Generuje zestaw ciał o zadanej charakterystyce, układ i charakterystyka prędkości."
                +" Konkretne wartości prędkości i masy są generowane losowo na podstawie podanych wartości. Ogólny charakter ruchu układu można wybrać"
                +" obok wyboru schematu rozmieszczenia."+"</p></html>\\");
        PresetButton = new JButton("Generuj!");

        PresetSliderLabel = new JLabel("Gęstość planet w schemacie:");
        PresetSliderLabel.setToolTipText("Wybiera ilość planet do wygenerowania w zadanym schemacie");

        PresetSlider = new JSlider(0, 100, 10);
        PresetSlider.setMinorTickSpacing(5);
        PresetSlider.setMajorTickSpacing(20);
        PresetSlider.setPaintTicks(true);
        PresetField = new JTextField("10",5);

        ResetButton = new JButton("Resetowanie układu (usuwa wszysztkie planety)");

        PresetPanel.add(PresetLabel);
        SettingsPresetBox.add(PresetSliderLabel);
        SettingsPresetBox.add(PresetSlider);
        SettingsPresetBox.add(PresetField);

        PlanetsPresetBox.add(PresetLabel);
        PlanetsPresetBox.add(PlanetPreset);
        PlanetsPresetBox.add(VelPreset);
        PlanetsPresetBox.add(MassPreset);

        Box PresetBox = Box.createVerticalBox();
        Box MainButtonBox = Box.createHorizontalBox();

        MainButtonBox.add(PresetButton);
        MainButtonBox.add(ResetButton);

        PresetBox.add(SettingsPresetBox);
        PresetBox.add(PlanetsPresetBox);
        PresetPanel.add(PresetBox);

        MenuPanel.add(GeneralInfo);
        GlobalBox.add(Box.createVerticalStrut(15));
        GlobalBox.add(TimeBox);
        GlobalBox.add(MassBox);
        GlobalBox.add(Box.createVerticalStrut(10));
        GlobalBox.add(ObjPresetBox);
        GlobalBox.add(Box.createVerticalStrut(10));
        GlobalBox.add(VelBox);
        GlobalBox.add(Box.createVerticalStrut(10));
        GlobalBox.add(LiveLogPanel);
        GlobalBox.add(scrollbar1);
        GlobalBox.add(Box.createVerticalStrut(10));
        GlobalBox.add(PresetPanel);
        GlobalBox.add(Box.createVerticalStrut(10));
        GlobalBox.add(MainButtonBox);

        MenuPanel.add(GlobalBox);
        //MenuPanel.add(ResetButton);
        MenuFrame.add(MenuPanel);
        MenuFrame.setVisible(true);
    }
    public MapPanel getMapPanel () {return Space;}

    public void LogEvent (String event)
    {
        LiveLog.append(event+"\n");
        LiveLog.setCaretPosition(LiveLog.getDocument().getLength());
    }

    public void display_stats(long time, int planets, double time_count)
    {
        FPSLabel.setText("FPS:  "+ 1000/(time+1) +"   Obiektów:  "+planets+"  Mnożnik Czasu:  "+time_count);
    }

    public void addTimeSliderListener (ChangeListener cl) { TimeSlider.addChangeListener(cl);}
    public void addMassSliderListener (ChangeListener cl) { MassSlider.addChangeListener(cl);}
    public void addTimePlusButtonListener (ActionListener al) {TimePlusButton.addActionListener(al);}
    public void addTimeMinusButtonListener (ActionListener al) {TimeMinusButton.addActionListener(al);}
    public void addObjSelectionListener(ActionListener al) {ObjPreset.addActionListener(al);}       //Planeta/gwiazda (blokuje odpowiednie elementy)
    public void addStarTimeSliderListener (ChangeListener cl) { StarTimeSlider.addChangeListener(cl);}  //czas życia gwiazdy
    public void addPresetSliderListener (ChangeListener cl) { PresetSlider.addChangeListener(cl);} //ilość planet w schemacie
    public void addResetButtonListener(ActionListener al) {ResetButton.addActionListener(al);}     //reset
    public void addPresetButtonListener (ActionListener al) {PresetButton.addActionListener(al);}   //generuj planety

    public void setTimeValueText () { TimeField.setText(Integer.toString(TimeSlider.getValue())); } //ustawia tekst na wartość z suwaka
    public void setTimeValueText ( int val) { TimeField.setText(Integer.toString(val)); }
    public void setTimeValueSlider (int time) {TimeSlider.setValue(time);}                          //ustawia suwak na wartość z tekstu
    public int getTimeSliderValue() {  return TimeSlider.getValue(); }
    public int getTimeTextValue ()                                                                  //pobiera z tekstu wartość czasu jeśli się da
    {
        try {return Integer.parseInt(TimeField.getText());}
        catch(NumberFormatException e) {return TimeSlider.getValue();}
    }

    public void setMassValueText () { MassField.setText(Integer.toString(MassSlider.getValue())); }

    public int getSelectedLogItem ()                                                                //Ilość informacji w logu
    {
      if(AllDetails.isSelected()) //pełne
          return 2;
      if(SomeDetails.isSelected())  //ważne
          return 1;
      else
          return 0;     //brak
    }

    public int getSelectedPresetItem () { return PlanetPreset.getSelectedIndex(); }   //schemat układu (siatka, spirala...)
    public int getSelectedVelPresetItem () { return VelPreset.getSelectedIndex(); }  //schemat prędkości (kołowa, brak, losowa...)
    public int getSelectedObj() {return ObjPreset.getSelectedIndex();}              //zwraca wybraną opcję (planeta / gwiazda)
    public int getSelectedMassPreset() {return MassPreset.getSelectedIndex();}     //zwraca wybrany schemat ustawiania mas dla generowanych układów

    public double getXvelFromText ()
    {
        try{return Double.parseDouble(XvelField.getText());}
        catch(NumberFormatException e) {return Math.random()*20-10;}
    }
    public double getYvelFromText ()
    {
        try{return Double.parseDouble(YvelField.getText());}
        catch(NumberFormatException e) {return Math.random()*20-10;}
    }
    public double getMassFromText ()
    {
        try{return Double.parseDouble(MassField.getText());}
        catch(NumberFormatException e) {return Math.random()*4000+150;}
    }
    public void setPresetTextValue () { PresetField.setText(Integer.toString(PresetSlider.getValue())); }   //ustawia ilość planet w tekście z wartości na suwaku
    public int getPresetTextValue ()                                                                        //pobiera wartość ilości planet z tekstu jeśli się da
    {
        try {return Integer.parseInt(PresetField.getText());}
        catch(NumberFormatException e) {return PresetSlider.getValue();}
    }
    public void setStarTimeText () {StarLiveTimeField.setText(Integer.toString(StarTimeSlider.getValue()));} //ustawia czas życia gwiazdy w tekście z wartości na suwaku
    public void setCorrectOptions ()                                //blokuje odpowiednie pola jeśli tworzona jest planeta i odblokowuje gdy gwiazda
    {
        if(ObjPreset.getSelectedIndex()==0)
        {
            StarTimeSlider.setEnabled(false);
            StarLiveTimeField.setEnabled(false);
        }
        else
        {
            StarTimeSlider.setEnabled(true);
            StarLiveTimeField.setEnabled(true);
        }
    }
    public int getStarTime ()                                           //pobiera wartość czasu życia gwiazdy z tekstu jeśli się da
    {
        try {return Integer.parseInt(StarLiveTimeField.getText());}
        catch(NumberFormatException e) {return StarTimeSlider.getValue();}
    }
    public boolean TracksSet ()
    {
        if(TracksCheck.isSelected())
            return true;
        else
            return false;
    }
    public boolean MoreLinesSet ()
    {
        if(MoreLinesCheck.isSelected())
            return true;
        else
            return false;
    }
}
