package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.*;

import supportGUI.Circle;
import supportGUI.Line;

public class DefaultTeam {

  // calculDiametre: ArrayList<Point> --> Line
  //   renvoie une pair de points de la liste, de distance maximum.
  public Line calculDiametre(ArrayList<Point> points) {
    System.out.println("je suis dans la fct calculDiametre");
    if (points.size()<3) {
      return null;
    }
    Point p=points.get(0);
    Point q=points.get(1);
    double maxDistance = distance(p, q);
    for (int i = 0; i < points.size(); i++) {
      for (int j = i + 1; j < points.size(); j++) {
        Point currentP = points.get(i);
        Point currentQ = points.get(j);
        double currentDistance = distance(currentP, currentQ);
        if (currentDistance > maxDistance) {
          p = currentP;
          q = currentQ;
          maxDistance = currentDistance;
        }
      }
    }
    return new Line(p,q);
  }

  // calculDiametreOptimise: ArrayList<Point> --> Line
  //   renvoie une pair de points de la liste, de distance maximum.
  public Line calculDiametreOptimise(ArrayList<Point> points) {
    if (points.size()<3) {
      return null;
    }

    Point p=points.get(1);
    Point q=points.get(2);

    /*******************
     * PARTIE A ECRIRE *
     *******************/
    return new Line(p,q);
  }

  // calculCercleMin: ArrayList<Point> --> Circle
  //   renvoie un cercle couvrant tout point de la liste, de rayon minimum.
  public Circle calculCercleMin(ArrayList<Point> points) {
    if (points.isEmpty()) {
      return null;
    }
    Point center=points.get(0);
    int radius=100;
    return new Circle(center,radius);
  }
  //   renvoie l'enveloppe convexe de la liste.
  public ArrayList<Point> enveloppeConvexe(ArrayList<Point> points){
    if (points.size()<3) {
      return null;
    }
    return exercice1(points);
  }

  private double crossProduct(Point p, Point q, Point s, Point t){
    return ((q.x-p.x)*(t.y-s.y)-(q.y-p.y)*(t.x-s.x));
  }
  private double distance (Point p, Point q){
    return Math.sqrt(Math.pow(p.getX() - q.getX(), 2) + Math.pow(p.getY() - q.getY(), 2));
  }
  private ArrayList<Point> exercice1(ArrayList<Point> points){
    if (points.size()<4) return points;

    ArrayList<Point> enveloppe = new ArrayList<Point>();

    for (Point p: points) {
      for (Point q: points) {
        if (p.equals(q)) continue;
        Point ref=p;
        for (Point r: points) if (crossProduct(p,q,p,r)!=0) {ref=r;break;}
        if (ref.equals(p)) {enveloppe.add(p); enveloppe.add(q); continue;}
        double signeRef = crossProduct(p,q,p,ref);
        boolean estCote = true;
        for (Point r: points) if (signeRef * crossProduct(p,q,p,r)<0) {estCote = false;break;} //ici sans le break le temps de calcul devient horrible
        if (estCote) {enveloppe.add(p); enveloppe.add(q);}
      }
    }

    return enveloppe; //ici l'enveloppe n'est pas trie dans le sens trigonometrique, et contient des doublons, mais tant pis!
  }

}
