import java.util.ArrayList;
import java.util.List;

/**
 * A state in the search represented by the (x,y) coordinates of the square and
 * the parent. In other words a (square,parent) pair where square is a Square,
 * parent is a State.
 * 
 * You should fill the getSuccessors(...) method of this class.
 * 
 */
public class State {

	private Square square;
	private State parent;

	// Maintain the gValue (the distance from start)
	// You may not need it for the BFS but you will
	// definitely need it for AStar
	private int gValue;

	// States are nodes in the search tree, therefore each has a depth.
	private int depth;

	/**
	 * @param square
	 *            current square
	 * @param parent
	 *            parent state
	 * @param gValue
	 *            total distance from start
	 */
	public State(Square square, State parent, int gValue, int depth) {
		this.square = square;
		this.parent = parent;
		this.gValue = gValue;
		this.depth = depth;
	}

	/**
	 * @param i j
	 *            the x and y coordinat of the state 
	 * @param maze
	 *            initial maze to get find the neighbors
	 * @param explored
	 *            explored[i][j] is true if (i,j) is already explored
	 *            
	 * @return true if there is no wall in the specific pasition
	 */
	private boolean wallCheck (int i, int j,Maze maze)
	{
		if (maze.getSquareValue(i, j) != '%')
		{
			return true;
		}
		return false;
	}
	
	/**
	 * @param explored
	 *            explored[i][j] is true if (i,j) is already explored
	 * @param maze
	 *            initial maze to get find the neighbors
	 * @return all the successors of the current state
	 */
	public ArrayList<State> getSuccessors(boolean[][] explored, Maze maze) {
		// FILL THIS METHOD

		// TODO check all four neighbors in left, down, right, up order
		// TODO remember that each successor's depth and gValue are
		// +1 of this object.
		
		int i = getX();
		int j = getY();
		
		ArrayList <State> expandStateList = new ArrayList <State>();
		
		//check left to see if there have wall or not
		if (wallCheck(i, j - 1,maze) && !explored[i][j-1] )
		{
			Square squareL = new Square(i, j - 1);
			State leftState = new State(squareL,this, gValue + 1, depth + 1);
			expandStateList.add(leftState);
		}
		
		//check down to see if there have wall or not
		if (wallCheck(i+1, j,maze) && !explored[i+1][j] )
		{
			Square squareD = new Square(i+1, j);
			State downState = new State(squareD,this, gValue + 1, depth + 1);
			expandStateList.add(downState);
		}
		
		//check right to see if there have wall or not
		if (wallCheck(i, j+1,maze) && !explored[i][j+1])
		{
			Square squareR = new Square(i, j+1);
			State rightState = new State(squareR,this, gValue + 1, depth + 1);
			expandStateList.add(rightState);
		}
		
		//check up to see if there have wall or not
		if (wallCheck(i-1, j,maze) && !explored[i-1][j])
		{
			Square squareU = new Square(i-1, j);
			State upState = new State(squareU,this, gValue + 1, depth + 1);
			expandStateList.add(upState);
		}
		
		return expandStateList;
	}

	/**
	 * @return x coordinate of the current state
	 */
	public int getX() {
		return square.X;
	}

	/**
	 * @return y coordinate of the current state
	 */
	public int getY() {
		return square.Y;
	}

	/**
	 * @param maze initial maze
	 * @return true is the current state is a goal state
	 */
	public boolean isGoal(Maze maze) {
		if (square.X == maze.getGoalSquare().X
				&& square.Y == maze.getGoalSquare().Y)
			return true;

		return false;
	}

	/**
	 * @return the current state's square representation
	 */
	public Square getSquare() {
		return square;
	}

	/**
	 * @return parent of the current state
	 */
	public State getParent() {
		return parent;
	}

	/**
	 * You may not need g() value in the BFS but you will need it in A-star
	 * search.
	 * 
	 * @return g() value of the current state
	 */
	public int getGValue() {
		return gValue;
	}

	/**
	 * @return depth of the state (node)
	 */
	public int getDepth() {
		return depth;
	}
}
