package algorithms;

import java.awt.Point;
import java.util.ArrayList;

import supportGUI.Circle;
import supportGUI.Line;

public class DefaultTeam {

  // calculDiametre: ArrayList<Point> --> Line
  //   renvoie une pair de points de la liste, de distance maximum.
  public Line calculDiametre(ArrayList<Point> points) {
    System.out.println("je suis dans la fct calculDiametre");
    if (points.size() < 3) {
      return null;
    }

    Point p = points.get(0);
    Point q = points.get(1);

    /*******************
     * PARTIE A ECRIRE *
     *******************/
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
    return new Line(p, q);
  }

  // calculDiametreOptimise: ArrayList<Point> --> Line
  //   renvoie une pair de points de la liste, de distance maximum.
  public Line calculDiametreOptimise(ArrayList<Point> points) {
    if (points.size() < 3) {
      return null;
    }

    Point p = points.get(1);
    Point q = points.get(2);

    /*******************
     * PARTIE A ECRIRE *
     *******************/
    return new Line(p, q);
  }

  // calculCercleMin: ArrayList<Point> --> Circle
  //   renvoie un cercle couvrant tout point de la liste, de rayon minimum.
  public Circle calculCercleMin(ArrayList<Point> points) {
    if (points.isEmpty()) {
      return null;
    }
    ArrayList<Point> rest = (ArrayList<Point>)points.clone();
    Point dummy=rest.get(0);
    Point p=dummy;
    for (Point s: rest) if (dummy.distance(s)>dummy.distance(p)) p=s;
    Point q=p;
    for (Point s: rest) if (p.distance(s)>p.distance(q)) q=s;
    double cX=.5*(p.x+q.x);
    double cY=.5*(p.y+q.y);
    double cRadius=.5*p.distance(q);
    rest.remove(p);
    rest.remove(q);
    while (!rest.isEmpty()){
      Point s=rest.remove(0);
      double distanceFromCToS=Math.sqrt((s.x-cX)*(s.x-cX)+(s.y-cY)*(s.y-cY));
      if (distanceFromCToS<=cRadius) continue;
      double cPrimeRadius=.5*(cRadius+distanceFromCToS);
      double alpha=cPrimeRadius/(double)(distanceFromCToS);
      double beta=(distanceFromCToS-cPrimeRadius)/(double)(distanceFromCToS);
      double cPrimeX=alpha*cX+beta*s.x;
      double cPrimeY=alpha*cY+beta*s.y;
      cRadius=cPrimeRadius;
      cX=cPrimeX;
      cY=cPrimeY;
    }
    return new Circle(new Point((int)cX,(int)cY),(int)cRadius);


   // Point center = points.get(0);
    //int radius = 100;

    //Circle circle = null;

    /*

    // Parcourir tous les points pour ajuster le cercle
    for (int i = 1; i < points.size(); i++) {
      Point currentPoint = points.get(i);

      // Calculer la distance entre le centre actuel et le point
      double distanceToCenter = distance(center, currentPoint);

      // Ajuster le rayon si nécessaire
      if (distanceToCenter > radius) {
        radius = (int) distanceToCenter;
      }
    }

     */

    // Créer et retourner le cercle couvrant minimum
    //return new Circle(center, radius);
  }
    // Méthode pour vérifier si un point est à l'intérieur du cercle


    /*******************
     * PARTIE A ECRIRE *
     *******************/
    //return new Circle(center,radius);

    //   renvoie l'enveloppe convexe de la liste.
    public ArrayList<Point> enveloppeConvexe(ArrayList<Point> points){
      if (points.size()<3) {
        return null;
      }
      return exercice1(triPanier(points));
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
  private ArrayList<Point> triPanier(ArrayList<Point> points){
    int maxX = Integer.MIN_VALUE;
    for (Point point : points) {
      maxX = Math.max(maxX, point.x);
    }
    Point[] Ymin = new Point[maxX];
    Point[] Ymax = new Point[maxX];
    for (Point p : points) {
      if(Ymin[p.x] ==null || Ymin[p.x].y > p.y){
        Ymin[p.x] = p;
      }
    }
    for (Point p : points) {
      if(Ymax[p.x] ==null || Ymax[p.x].y < p.y){
        Ymax[p.x] = p;
      }
    }
    ArrayList<Point> enveloppe = new ArrayList<>();
    for (Point p : Ymin) {
      if (p != null) {
        enveloppe.add(p);
      }
    }
    for (Point p : Ymax) {
      if (p != null && !enveloppe.contains(p)) {
        enveloppe.add(p);
      }
    }
    return enveloppe;
  }

  private boolean estDansCercle (Point point, Circle circle){
    double distance = distance(circle.getCenter(), point);
    return distance <= circle.getRadius();
  }
  // Méthode pour calculer le milieu entre deux points
  private Point milieu(Point p, Point q) {
    double x = (p.getX() + q.getX()) / 2;
    double y = (p.getY() + q.getY()) / 2;
    return new Point((int) x, (int) y);
  }

}
