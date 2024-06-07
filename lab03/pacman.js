
/*  James Billinger
    300187686
    June 7th, 2024
    CSI3140[Z]
*/

function createGame(n) {
    let board = new Array(n).fill(".");
    let positions = new Set();

    function getRandom() {
        let val = Math.floor(Math.random() * n);

        while (positions.has(val)) {
            val = Math.floor(Math.random() * n);
        }
        positions.add(val);
        return val;
    }

    let pacmanPosition = getRandom();
    board[pacmanPosition] = "C";

    let fruitPosition = getRandom();
    board[fruitPosition] = "@";

    let ghostPosition = getRandom();
    board[ghostPosition] = "^";

    let gameState = {
        board: board,
        score: 0,
        level: 1
    };

    console.log(gameState);
    return gameState;
}


function moveLeft(gameState) {
    let game = gameState.board;
    const pacmanPosition = game.indexOf("C");

    if (pacmanPosition > 0) {
        if (game[pacmanPosition - 1] === ".") {
            gameState.score += 1;
        }
        game[pacmanPosition] = "";  
        game[pacmanPosition - 1] = "C";  
    }

    if (game.includes(".") == false) {
        gameState = nextLevel(gameState, game.length);
    }

    console.log(gameState);
    return gameState;
}

function moveRight(gameState) {
    let game = gameState.board;
    const pacmanPosition = game.indexOf("C");

    if (pacmanPosition < game.length - 1) {
        if (game[pacmanPosition + 1] === ".") {
            gameState.score += 1;
        }
        game[pacmanPosition] = "";  
        game[pacmanPosition + 1] = "C";  
    }

    if (game.includes(".") == false) {
        gameState = nextLevel(gameState, game.length);
    }

    console.log(gameState);
    return gameState;
}

function nextLevel(gameState, n) {
    let board = new Array(n).fill(".");
    let positions = new Set();

    function getRandom() {
        let val = Math.floor(Math.random() * n);
        
        while (positions.has(val)) {
            val = Math.floor(Math.random() * n);
        }
        positions.add(val);
        return val;
    }

    let pacmanPosition = getRandom();
    board[pacmanPosition] = "C";

    let fruitPosition = getRandom();
    board[fruitPosition] = "@";

    let ghostPosition = getRandom();
    board[ghostPosition] = "^";

    gameState.board = board;
    gameState.level += 1;

    console.log("advance to level " + gameState.level);
    return gameState;
}




//test
let gameState = createGame(20);
gameState = moveLeft(gameState);  
gameState = moveRight(gameState); 
gameState = moveRight(gameState); 
gameState = moveRight(gameState); 
gameState = moveRight(gameState); 
gameState = moveRight(gameState); 




