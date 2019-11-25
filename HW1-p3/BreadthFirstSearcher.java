import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

// import sun.java2d.StateTrackable.State;

/**
 * Breadth-First Search (BFS)
 * 
 * You should fill the search() method of this class.
 */
public class BreadthFirstSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze initial maze.
	 */
	public BreadthFirstSearcher(Maze maze) {
		super(maze);
	}

	/**
	 * Main breadth first search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {
		// explored list is a 2D Boolean array that indicates if a state associated with a given position in the maze has already been explored.
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];
		// Queue implementing the Frontier list
		LinkedList<State> queue = new LinkedList<State>();
		queue.add(new State(maze.getPlayerSquare(), null, 0, 0));

		while (!queue.isEmpty()) {
			if(queue.size() > maxSizeOfFrontier) {
				maxSizeOfFrontier = queue.size();
			}
			
			State state = queue.pop();
			noOfNodesExpanded++;
			// Add the node to the explored set
			explored[state.getX()][state.getY()] = true;

									
			if(state.getDepth() > maxDepthSearched) {
				maxDepthSearched = state.getDepth();
			}
			// return true  if find a solution
			if (state.isGoal(maze)) {
				state = state.getParent();
				cost = 1;
				while(state != null && state.getParent() != null) {
					maze.setOneSquare(state.getSquare(), '.');
					state = state.getParent();
					cost++;
				}
				return true;
			}
			
			// Adding the resulting nodes to the frontier 
			ArrayList<State> successor = state.getSuccessors(explored, maze);
			//boolean found = true;
//			for (State currState : successor) {
//				System.out.println(222);
//				for (State frontier: queue) {
//					System.out.println(333);
//					if (currState.getX() != frontier.getX() || currState.getY() != frontier.getY()) {
//						found = false;
//					} else if (currState.getX() == frontier.getX() && currState.getY() == frontier.getY()){
//						found = true;
//						break;
//					}
//				}
//				
//				System.out.println(444);
//				if(!found && !explored[currState.getX()][currState.getY()]) {
//					queue.add(currState);
//				}
//				System.out.println(555);
////				if(!queue.contains(currState) && !explored[currState.getX()][currState.getY()]) {
////					queue.add(currState);
////				}
//			}
			for (int i =0; i< successor.size(); i++) {
				boolean found = false;
				for (int j = 0; j< queue.size(); j++) {
					if (successor.get(i).getX() == queue.get(j).getX() && 
						successor.get(i).getY() == queue.get(j).getY()) {
						found = true;
					}
				}
				if(!found && !explored[successor.get(i).getX()][successor.get(i).getY()]) {
					queue.add(successor.get(i));
				}
			}

		}

		// TODO return false if no solution
		return false;
	}
}
