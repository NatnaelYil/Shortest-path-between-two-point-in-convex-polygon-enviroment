//
//  java program that finds the shortest distance between two points, needs a littel tweeking
//  on overiding function the prority queue inorder to store the smallest heuristice distance at the top
////////////////////////////////////////////////////////////////
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.lang.Math;
import java.util.PriorityQueue;   

//Coordinate class is used to take the input Coordinates from a text file and creat a linked list for each convex polygon
class Coordinate  implements Comparable<Coordinate>

{     
// this function over right the comapare method for the prioty queue in order to stor the heuristice with the smallest F(n) to the top
   @Override
    public int compareTo(Coordinate c)
   
   {   int x=(int)this.f;
      int y=(int)c.f;   
      if (this.equals(c))
         return 0;
      else if (this.f>c.f)
         return 1;
      else
         return -1;
     
   
   }
     
   public boolean equals(Coordinate c)
   { 
      return this.f==c.f;
   }    
   public double x;              // variable to store the x- coordinate
   public double y;              // variable to store the y-coordinate
   public double h=0;            //stores the straight line distance from the destenation
   public double g=0;            //stores the distance so far
   public double f=0;            // stores estimated distance
   public boolean isvisited=false; //variable if a vertexs has been visted or not
   public Coordinate next;
   public Coordinate parent;              // keeps track of the parent coordinate which has called it
// -------------------------------------------------------------
   public Coordinate(double id, double dd) // constructor
   {
      x = id;                 // initialize the coordinates
      y = dd;                 
   }                           
// -------------------------------------------------------------
   public boolean isvisited()   // to check whether a variable has been visted or not
   {  
      return isvisited;
   }
   //--------------------------------------------------------------
   public void displayCoordinate()      // display the coordinate
   {
      System.out.print("(" + x + ", " + y + ") ;");
   }
}  // end class Link
////////////////////////////////////////////////////////////////


class Poly
{
   private Coordinate first;            // ref to first coordinate on of a polygone

// -------------------------------------------------------------
   public Poly()              // constructor
   {
      first = null;               // no coordinates on list yet
   }
// -------------------------------------------------------------
   public boolean isEmpty()       // true if the polygone is empty is empty
   {
      return (first==null);
   }
// -------------------------------------------------------------
                                  // insert at start of list
   public void add(int x, int y)
   {                           // make new link
      Coordinate newcoordinate = new Coordinate(x, y);
      newcoordinate.next = first;       // newLink --> old first
      first = newcoordinate;            // first --> newLink
   }
// -------------------------------------------------------------
   public Coordinate first()      
   {                                    
      return first;                // return the first coordinate
   }
//--------------------------------------------------------------
         
// -------------------------------------------------------------
   public void displayPolygon()
   {
      System.out.print("Coordinate (first-->last): ");
      Coordinate current = first;       // start at beginning of the beggining of a coordinate
      while(current != null)      // until end of the polygon,
      {
         current.displayCoordinate();   // print the coordinate
         current = current.next;  // move to next link
      }
      System.out.println("");
   }
      // -------------------------------------------------------------
}  // end class LinkList
   







//---------------------------------------------------------------------------------
class shortestpath
{
   public static Coordinate[] edges =new Coordinate[2];                          //use to store the edges of a coordinate
   //public static  ArrayList<Coordinate> AList = new ArrayList<Coordinate>();   
   public static  Stack<Coordinate> stack = new Stack<Coordinate>();             // a stack used to sore the coordinates to be expanded 
   public static  Stack<Coordinate> stack2 = new Stack<Coordinate>();            // a stack used to store the expaned coordinate in order to calculate g(n),h(n),f(n)
   public static  PriorityQueue<Coordinate> q= new PriorityQueue<Coordinate>();  // to store the coordinates whose f(n) have been evaluated store in ascending order of distance
   //public static  Queue<Coordinate>  pq1 = new LinkedList<Coordinate>();
   public static  Coordinate goal;                                               // stores the goal distance 
   public static void main(String[] args)
   {
      Coordinate starting= new Coordinate(0,0);
      Coordinate dest= new Coordinate(0,0);
      //int [] Start = new int [2];                                             
      //int[] Dest = new int[2];
      int NumberofPolygones=0;                                                   // used to store the number of polygon in the text file 
              
     // ArrayList obstacles = new ArrayList( );
        // make new list
     
      
      if (args.length <1 )                                                       // if file not present- display an error
      {    System.out.print("Unable to read file");
      }
              
      File file = new File(args[0]);                                             //reads the file for the number of poylgons in the file
      File file2 = new File(args[0]);                                            // reads the file to store the coordinates from file
      
      
    // reads the file for the number of polygones and catches any exceptions  
      try (BufferedReader br = new BufferedReader(new FileReader(file2))) {            
         String line;
         while ((line = br.readLine()) != null) {
                      // process the line.
            NumberofPolygones++;
         }
                                      
         br.close();            
      } 
      catch (IOException e) {
         System.out.println("Cannot read file: " + args[0]);              //handles exceptions
         System.exit(0);
      }
      
      
      
      NumberofPolygones=NumberofPolygones-2;                             //assign the number of polygone links to be creted        
   
      System.out.println("NumberofPolygones--"+NumberofPolygones);
      Poly[] poly = new Poly[NumberofPolygones];                        // create array of polygone 
       
                  
      try {
      
            // Open input stream
         Scanner scan = new Scanner(file).useDelimiter(",;\n");
         int i=-1,j=0;
               
               
         while (scan.hasNextLine())
         {                                                           //scans line by line change
            i++;                                                        
         
                 
            if (i>1)
               poly[j]=new Poly();
            
            String line = scan.nextLine(); 
            String[] words = line.split(";");
                               
                                                                     //For each word in the line
            for(String str : words) {
               String[] word = str.split(",");                       // split it into by , and ; and add to polygon
            
               try {
                  if (i==0)
                  {
                     starting.x=Integer.parseInt(word[0].trim());
                     starting.y=Integer.parseInt(word[1].trim());
                  }
                  else if (i==1) 
                  {  
                     dest.x=Integer.parseInt(word[0].trim());
                     dest.y=Integer.parseInt(word[1].trim());
                     j=-1;                                   } 
                  else{
                     int x = Integer.parseInt(word[0].trim());
                     int y =Integer.parseInt(word[1].trim());
                     poly[j].add(x,y);     
                                            
                  }
                                 
                                 
                                     
               }
               catch(NumberFormatException nfe) { };         
                                                                                                               
                            
            }                                   
                                                            
                                                                             
            j++;                            
             }                                                           //end while 
      
                                                                         // Close input stream
         scan.close();
      
      } 
      catch (IOException e) {
         System.out.println("Cannot read file: " + args[0]);               //  catch errors
         System.exit(0);
      }
      
      
      System.out.println("Starting    Coordinates-: " + starting.x+","+starting.y);
      System.out.println("Destenation Coordinates-: " + dest.x+","+dest.y);
      for (int i=0;i<poly.length;i++)                                               //displays the polygones
      {poly[i].displayPolygon();
      } 
          
      Coordinate start= new Coordinate(starting.x,starting.y);                      //create a starting coordinate
      goal= new Coordinate(dest.x,dest.y);                                          //create a goal coordinate
      
      start.parent= new Coordinate(0,0);                                             //assign the parent coordinate of starting coordinate 0
     
                
      PathFinder(poly,start);                                                        // calls for the shortest path using A* search
                    
      
     
     
   }  // end main()
   
   
   
   
   
   // this function takes the coordinate that needs to be explore and also calls the evaluation for each coordinate visible
   
   public static void PathFinder(Poly[] poly,Coordinate start)
   {
   
     
      stack.push(start);                                                              // push start to the stack to be expaneded for reachable nodes
     
      while (!stack.empty())                                                           // while loop to see if there are coordinates on the stack to be expaned  
      { 
         if (stack.peek().x== goal.x && stack.peek().y==goal.y )                       // if the top coordinate is the gooal 
         { 
            System.out.println("\n\nFound My Path..");
            Coordinate path=stack.peek();                                              // store the coordiante on new  coor't path 
            while(path.parent!=null)                                                    // while the parent of goal is null, back track the path it came from
            {  
               System.out.println("path-----("+(int)path.x+","+(int)path.y+")");
               path=path.parent;
            }
            break;                                                                      // exit the loop
         }
         
         if (stack.peek().isvisited==true)                                              //if the top element of the stack has already been expanded then remove it
            stack.pop();
         else
         {
            findSuccessors(poly,stack.peek());                                          // find the visble node  and assgin it to stack2 for f(n) evaluation                                       
            stack.peek().isvisited=true;                                                // mark the top element on the stack visited
           
            while(!stack2.empty())                                                       //until all the coordiante's f(n) have been 
            {                    
               if (stack2.peek().isvisited==true)                                        //if the top coordiante on stack2 has been evaluated, then remove it
                  stack2.pop();
               else if (stack2.peek().f==0)                                              // if it hast been evaluated 
               {     
                  stack2.peek().parent=stack.peek();                                     // assign the parent node from the top element on the stack  
                  stack2.peek().g= stack2.peek().parent.g +distance(stack2.peek(),stack2.peek().parent);    // evaluate the g(n)
               
                  stack2.peek().h=heuristic(stack2.peek(),goal);                         // calls the heuristic function to evalute 
                  stack2.peek().f=stack2.peek().g+stack2.peek().h;                       //aassgin the f(n) to that coordinate
                  q.add(stack2.pop());                                                   //add the evaluated coordiante to the queue
               }                  
            }
            stack.pop();
            while (q.peek().isvisited==true)
               q.remove();
            stack.push(q.remove());
            
         }
      }
   
   
   
   
   
   }
 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 // this fuction draws a line from one coordinate to another and   
   
   
   public static void findSuccessors(Poly[] poly,Coordinate line11)
   {    
      int j=0;
   
      boolean isvisible=false;
   
      Coordinate line12=new Coordinate(0,14);;                                   // for the second coordinate where the line is drawn too
      Coordinate temp11;                                                         // to check with each sides of the polygon
      Coordinate temp12;
      Coordinate first;
      while(j<=poly.length)                                                     // starting from the fist polygone- first link
      {
         if (j==poly.length)
            line12=goal;
         else
            line12=poly[j].first();
         while (line12!=null)
         {  isvisible=false;
            if (line11.x==line12.x && line11.y==line12.y)                        // we check to see for edges of the same polygon 
            { findedges(poly,line12,j);                                          // find the edges
              
               {
                  if (!(line11.parent.x==edges[0].x && line11.parent.y==edges[0].y))         
                  { Coordinate zero=new Coordinate(edges[0].x,edges[0].y);    
                     stack2.push(zero);                                                //push them to the stack
                  }
                  if (!(line11.parent.x==edges[1].x && line11.parent.y==edges[1].y))    //only if they arent parent edges
                  {
                     Coordinate one=new Coordinate(edges[1].x,edges[1].y);
                     stack2.push(one);
                  }
                  line12=line12.next;
               }
               break;
            }
            outerloop:
            for (int i=0;i<poly.length;i++)                    
            {
               temp11=poly[i].first();                                              //or else start with the first coordinate of that polygon
               first = temp11;             
               while (temp11.next!=null)                                            //until you reach the end - check for intersection
               
               {   temp12=temp11.next;                                              // if there is an intersection
                  if ((Line2D.linesIntersect(line11.x,line11.y,line12.x,line12.y,temp11.x,temp11.y,temp12.x,temp12.y))  )  
                  {  
                     if(i==j)                                                       // check to see if you are on the same polygone
                     {    findedges(poly,line12,i);
                        if (temp12.x==edges[1].x && temp12.y==edges[1].y || temp11.x==edges[0].x && temp11.y==edges[0].y)  
                           isvisible=true;                                         //  and mark that line visible
                        else
                        {isvisible=false;                                           // if line intersects ,drop the coordinate and move to the next coordinate to check visibility
                           break outerloop;
                        }
                     }
                     else if (ispolycoordinate(line11,poly)==i)                     // again check for edges      
                     { findedges(poly,line11,i);
                        if (temp12.x==edges[1].x && temp12.y==edges[1].y || temp11.x==edges[0].x && temp11.y==edges[0].y)  
                           isvisible=true;
                        else  
                        {isvisible=false;
                           break outerloop;
                        }
                     }
                                                
                     
                     else  
                     {isvisible=false;
                        break outerloop;
                     }
                  }
                  temp11=temp11.next;
                  if (temp11.next==null)
                  { temp12=first;
                     if ((Line2D.linesIntersect(line11.x,line11.y,line12.x,line12.y,temp11.x,temp11.y,temp12.x,temp12.y))  )
                     { 
                        if(i==j)
                        {findedges(poly,line12,i);
                           if (temp12.x==edges[1].x && temp12.y==edges[1].y || temp11.x==edges[0].x && temp11.y==edges[0].y)  
                              isvisible=true;
                           else
                           {isvisible=false;
                              break outerloop;
                           }
                        }
                        else if (ispolycoordinate(line11,poly)==i)
                        { findedges(poly,line11,i);
                           if (temp12.x==edges[1].x && temp12.y==edges[1].y || temp11.x==edges[0].x && temp11.y==edges[0].y)  
                              isvisible=true;
                        }
                        
                        else
                        {isvisible=false;
                           break outerloop;
                        }
                     }  
                  }
                 
               }
            
            
            }
            if(isvisible && line12.x!= line11.x && line11.y!=line12.y)                 // if this coordinate can be reached ..add to stack for evaluation
            { 
               Coordinate one=new Coordinate(line12.x,line12.y);
               stack2.push(one);
              
            }
            line12=line12.next;                                                           // go to the next coordinate
         
         }  
         j++;
      }
     
   
   
   
   }
  /////////////////////////////////////////////////////////////////////////////////////////////////////
// this function evaluates if a given coordinate is of a given polygon for edge extraction
   public static int ispolycoordinate (Coordinate x, Poly [] poly)
   {    
      for (int i=0; i<poly.length;i++)
      {  
         Coordinate search= poly[i].first();
         while (search.next!= null)
         {  
            if (x.x==search.x && x.y==search.y ) 
               return i;
              
            search=search.next;
            if (search.next== null)
            { 
               if (x.x==search.x && x.y==search.y ) 
                  return i;
            }
         }
               
      }
   
      return -1;
   }  
   
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // this function finds an edge for a given coordinate and assign those edges to a global variable for evaluation    
   public static void findedges(Poly[] poly,Coordinate x, int i)
   { 
      Coordinate search=poly[i].first();
      Coordinate back=null;
      outerloop:
      while (search!=null)
      {  
         if (x.x==search.x && x.y==search.y && search==poly[i].first()) 
         { edges[1]=search.next;   
            while (search.next!=null)                  
            { search=search.next;
               if (search.next== null)
               {edges[0]=search;
                  break outerloop; }
            }
         } 
         else if (x.x==search.x && x.y==search.y && search.next ==null)
         {     edges[0]=back; 
            edges[1]=poly[i].first();
            break;
         }
         else
         {  back=search; 
            search=search.next;
            {  edges[0]=back;            
               edges[1]=search.next;  
               if (x.x==search.x && x.y==search.y && search.next ==null && edges[1]==null)
                  edges[0]=poly[i].first();
               else if (x.x==search.x && x.y==search.y)
                  break;
                     
            }
         }
      }
             
   }
   
   /////////////////////////////////////////////////////////////////////////////////////////////////////////////
   // this function evaluates the distance between two points 
   public static double distance(Coordinate x, Coordinate y)
   {   
   
      return ((Math.sqrt(Math.pow((x.x-y.x), 2) + Math.pow((x.y-y.y), 2))));
   
   }
   ////////////////////////////////////////////////////////////////////////////////////////////////////////
  // this function evaluates the estimated distance between the current coordinate and the goal distance
   public static double heuristic (Coordinate x,Coordinate des)
   { double h=0;
      
      h=distance(des,x);
      return h;
   }
      
}  // end class LinkListApp
////////////////////////////////////////////////////////////////
