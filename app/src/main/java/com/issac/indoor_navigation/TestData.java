package com.issac.indoor_navigation;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

/**
 * TestData
 *
 * @author: onlylemi
 */
public final class TestData {

    private TestData() {}

    public static List<PointF> getNodesList() {
        List<PointF> nodes = new ArrayList<>();
        nodes.add(new PointF(4215,4505));

        nodes.add(new PointF(4160,4410));
        nodes.add(new PointF(4090,4410));
        nodes.add(new PointF(4020,4410));
        nodes.add(new PointF(3950,4410));

        nodes.add(new PointF(3910,4505));

        nodes.add(new PointF(3950,4250));
        nodes.add(new PointF(4020,4250));
        nodes.add(new PointF(4090,4250));
        nodes.add(new PointF(4160,4250));

        nodes.add(new PointF(4240,4250));
        nodes.add(new PointF(4240,4337));
        nodes.add(new PointF(4240,4410));

        nodes.add(new PointF(4090,4465));
        nodes.add(new PointF(4020,4465));
        nodes.add(new PointF(3950,4465));

        nodes.add(new PointF(3950,4337));
        nodes.add(new PointF(4020,4337));
        nodes.add(new PointF(4090,4337));

        nodes.add(new PointF(4090,4190));
        nodes.add(new PointF(4020,4190));
        nodes.add(new PointF(3950,4190));

        nodes.add(new PointF(4220,4337));

        return nodes;
    }

    public static List<PointF> getNodesContactList() {
        List<PointF> nodesContact = new ArrayList<PointF>();
        nodesContact.add(new PointF(0,1));
        nodesContact.add(new PointF(0,12));
        nodesContact.add(new PointF(1,2));
        nodesContact.add(new PointF(1,9));
        nodesContact.add(new PointF(1,12));
        nodesContact.add(new PointF(2,3));
        nodesContact.add(new PointF(2,13));
        nodesContact.add(new PointF(2,18));
        nodesContact.add(new PointF(3,4));
        nodesContact.add(new PointF(3,14));
        nodesContact.add(new PointF(3,17));
        nodesContact.add(new PointF(4,5));
        nodesContact.add(new PointF(4,15));
        nodesContact.add(new PointF(4,16));
        nodesContact.add(new PointF(6,16));
        nodesContact.add(new PointF(6,21));
        nodesContact.add(new PointF(6,7));
        nodesContact.add(new PointF(7,8));
        nodesContact.add(new PointF(7,20));
        nodesContact.add(new PointF(7,17));
        nodesContact.add(new PointF(8,18));
        nodesContact.add(new PointF(8,9));
        nodesContact.add(new PointF(8,19));
        nodesContact.add(new PointF(9,10));
        nodesContact.add(new PointF(10,11));
        nodesContact.add(new PointF(11,12));
        nodesContact.add(new PointF(11,22));

        return nodesContact;
    }

    public static List<PointF> getMarks() {
        List<PointF> marks = new ArrayList<>();
        //real marks
        marks.add(new PointF(4215,4505));   //front door
        marks.add(new PointF(3910,4505));   //back door
        //Right
        marks.add(new PointF(4090,4465));   //first line
        marks.add(new PointF(4020,4465));   //second line
        marks.add(new PointF(3950,4465));   //third line
        //Medium
        marks.add(new PointF(3950,4337));   //third line
        marks.add(new PointF(4020,4337));   //second line
        marks.add(new PointF(4090,4337));   //first line

        marks.add(new PointF(4220,4337));   //teacher's desk
        //Left
        marks.add(new PointF(4090,4190));   //first line
        marks.add(new PointF(4020,4190));   //second line
        marks.add(new PointF(3950,4190));   //third line

        return marks;
    }

    public static List<String> getMarksName() {
        List<String> marksName = new ArrayList<>();
        for (int i = 0; i < getMarks().size(); i++) {
            marksName.add("Shop " + (i + 1));
        }
        return marksName;
    }
}
