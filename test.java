public final class Test {

	public final static int DEAD = 0x00;

	public final static int LIVE = 0x01;

	public final static void main(String[] args) {

		Test game = new Test();
		game.test(5);
	}

	private void test(int nrIterations) {

		int[][] board = { { DEAD, DEAD, DEAD, DEAD, DEAD }, { DEAD, DEAD, DEAD, LIVE, DEAD },
				{ DEAD, DEAD, LIVE, LIVE, DEAD }, { DEAD, DEAD, DEAD, LIVE, DEAD }, { DEAD, DEAD, DEAD, DEAD, DEAD } };

		System.out.println("Output");
		DisplayBoard(board);

		for (int i = 0; i < nrIterations; i++) {
			System.out.println();
			board = get_Next_Board(board);
			DisplayBoard(board);
		}
	}

	private void DisplayBoard(int[][] board) {

		for (int i = 0, e = board.length; i < e; i++) {

			for (int j = 0, f = board[i].length; j < f; j++) {
				System.out.print(Integer.toString(board[i][j]) + ",");
			}
			System.out.println();
		}
	}

	public int[][] get_Next_Board(int[][] board) {

		if (board.length == 0 || board[0].length == 0) {
			throw new IllegalArgumentException("Board must have a positive amount of rows and/or columns");
		}

		int nrRows = board.length;
		int nrCols = board[0].length;

		int[][] buf = new int[nrRows][nrCols];

		for (int row = 0; row < nrRows; row++) {

			for (int col = 0; col < nrCols; col++) {
				buf[row][col] = N_CellState(board[row][col], L_Neighbours(row, col, board));
			}
		}
		return buf;
	}

	private int L_Neighbours(int cellRow, int cellCol, int[][] board) {

		int liveNeighbours = 0;
		int rowEnd = Math.min(board.length, cellRow + 2);
		int colEnd = Math.min(board[0].length, cellCol + 2);

		for (int row = Math.max(0, cellRow - 1); row < rowEnd; row++) {

			for (int col = Math.max(0, cellCol - 1); col < colEnd; col++) {

				if ((row != cellRow || col != cellCol) && board[row][col] == LIVE) {
					liveNeighbours++;
				}
			}
		}
		return liveNeighbours;
	}

	private int N_CellState(int curState, int liveNeighbours) {

		int newState = curState;

		switch (curState) {
		case LIVE:

			if (liveNeighbours < 2) {
				newState = DEAD;
			}

			if (liveNeighbours == 2 || liveNeighbours == 3) {
				newState = LIVE;
			}

			if (liveNeighbours > 3) {
				newState = DEAD;
			}
			break;

		case DEAD:

			if (liveNeighbours == 3) {
				newState = LIVE;
			}
			break;

		default:
			throw new IllegalArgumentException("State of cell must be either LIVE or DEAD");
		}
		return newState;
	}
}
