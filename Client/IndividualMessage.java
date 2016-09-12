package Client;

import javax.swing.*;
import javax.swing.border.LineBorder;

import utility.FontLibrary;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class IndividualMessage extends JPanel {
    private ImageIcon profilePicture;
    private JLabel senderNameLabel;
    private Box messageBox;
    private String senderName;
    private User recipient;
    private Conversation associatedConversation;
    public MessagePage m;
	final Color color = new Color(250,243,233);
	private Font font = FontLibrary.getTrueTypeFont(28);
	private Font customFont = FontLibrary.getTrueTypeFont(14);

    //pass in profile picture when we're ready
    //pass in a string and a conversation
    public IndividualMessage(Conversation c, boolean b, MessagePage m) {
        //this.profilePicture = profilePicture;
        associatedConversation = c;
        this.m = m;
        recipient = c.getRecipient();
        senderName = recipient.getFullName();
        senderNameLabel = new JLabel(senderName);
        //senderNameLabel.setFont(customFont);
        if (b) {
            senderNameLabel.setFont(font);

        } else {
            senderNameLabel.setFont(font);

        }


        //TODO: reposition text on the left of the box
        messageBox = Box.createHorizontalBox();
        messageBox.add(senderNameLabel);


        this.setBorder(new LineBorder(Color.GRAY));
        messageBox.setBackground(new Color(250,243,233));
        add(messageBox);
        setVisible(true);
        addEvents();
        System.out.println("Constructor completed for : " + senderName);
        this.setBackground(color);
    }

    public void addEvents() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("name was clicked");
                associatedConversation.setVisible(true);
                associatedConversation.lastopened =  System.currentTimeMillis();
                m.revalidate();
                m.repaint();
            }
        });

    }
}
