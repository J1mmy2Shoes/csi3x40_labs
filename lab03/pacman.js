function createGame(n) {
    
    let board = new Array(n).fill(".");
    let positions = new Set();

    
    function getRandom() {
        let pos;
        do {
            pos = Math.floor(Math.random() * n);
        } while (positions.has(pos));
        positions.add(pos);
        return pos;
    }

    
    let pacmanPos = getRandom();
    board[pacmanPos] = "C";

    let fruitPos = getRandom();
    board[fruitPos] = "@";
    
    let ghostPos = getRandom();
    board[ghostPos] = "^";



    console.log(board);
    return board;
}



// Test the createGame function
createGame(12);