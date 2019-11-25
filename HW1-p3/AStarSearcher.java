import java.util.ArrayList;
import java.util.PriorityQueue;
import java.lang.Math;

/**
 * A* algorithm search
 * 
 * You should fill the search() method of this class.
 */
public class AStarSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze initial maze.
	 */
	public AStarSearcher(Maze maze) {
		super(maze);
	}
	
	private double f(State s) {
	    // f(n) = g(n)+ h(n). 
	    return s.getGValue() + h(s);
	  }

	private double h(State s) {
	   Square g = maze.getGoalSquare();
	   return Math.sqrt((s.getX() - g.X) * (s.getX() - g.X) + (s.getY() - g.Y) * (s.getY() - g.Y));
	   
	}

	/**
	 * Main a-star search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {
		
		// frontier is a priority queue ordered by f(n)=h(n)+g(n)
		PriorityQueue<StateFValuePair> frontier = new PriorityQueue<StateFValuePair>();
		
		//initialize the start state
		State startState = new State(maze.getPlayerSquare(), null, 0, 0); 
        frontier.add(new StateFValuePair(startState,f(startState)));
        
		// explored list is a Boolean array that indicates if a state associated with a given position in the maze has already been explored. 
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];
		
		while (!frontier.isEmpty()) {
			
			if(frontier.size() > maxSizeOfFrontier) {
	        	  maxSizeOfFrontier = frontier.size();
	        }
			
			// pop the lowest-cost node of frontier
			StateFValuePair s = frontier.poll();
			// add node.STATE to explored
			explored[s.getState().getX()][s.getState().getY()] = true;
			// the number of nodes expanded (removed from frontier) increase
			noOfNodesExpanded++;
			
			if(s.getState().getDepth() > maxDepthSearched) {
				maxDepthSearched = s.getState().getDepth();
            }
			
		   // if problem.GOAL-TEST(node.STATE) then return SOLUTION(node)
           if(s.getState().isGoal(maze)) {
        	   cost = s.getState().getGValue();
			   State st = s.getState();
        	   st = st.getParent();
        	  while(st != null && st.getParent() != null) {
        		  maze.setOneSquare(st.getSquare(), '.');
        		  st = st.getParent();
        	  }
        	  return true;
           }

           // get the list of successor node
           ArrayList<State> succ = s.getState().getSuccessors(explored, maze);
           
           PriorityQueue<StateFValuePair> frontier2 = new PriorityQueue<StateFValuePair>();
           //frontier2.addAll(frontier);
           
           for (int i = 0 ;i<succ.size();i++) {
        	   // check if the successor is in frontier or not
        	   boolean found = false;
        	   State st = succ.get(i);
        	   StateFValuePair child =
        	            new StateFValuePair(st, f(st));
        	   int frontierSize = frontier.size();
        	   
        	   while(frontierSize > 0) {
        		   StateFValuePair frontierHead = frontier.poll();
        	       if (child.getState().equals(frontierHead.getState())) {
        	            found = true;
        	            if (child.compareTo(frontierHead) == -1) {
        	              frontierHead = child;
        	              frontier2.add(frontierHead);
        	            }

        	          }
        	          if (!child.getState().equals(frontierHead.getState())) {
        	        	frontier2.add(frontierHead);
        	            explored[child.getState().getX()][child.getState().getY()] = true;
        	          }
        	          frontierSize--;
        	   }
        	   if (found == false) {
        		   	frontier2.add(child);
        	        explored[child.getState().getX()][child.getState().getY()] = true;
        	   }
        	   int frontier2Size = frontier2.size();
               for (int j = 0; j < frontier2Size; j++) {
                 frontier.add(frontier2.poll());
               }
           }
		}
    return false;
	}    
}
