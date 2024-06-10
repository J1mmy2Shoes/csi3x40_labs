/*  James Billinger
    300187686
    June 10th, 2024
    CSI3140[Z]
*/

console.log('yatzy_game.js hello world');


class YatzyGame {
    constructor() {
        this.gameConstruct();
    }

    gameConstruct() {
        this.numberOfRoles = 0;
        this.Values = [1, 1, 1, 1, 1];
        this.diceToKeep = [false, false, false, false, false];
    }

    rollDice() {
        if (this.rollNumber < 3) {
            for (let i = 0; i < 5; i++) {
                if (this.diceToKeep[i] === false) {
                    this.Values[i] = Math.floor(Math.random() * 6) + 1;
                }
            }
            this.rollNumber++;
        } 
        else {
            console.log("You already rolled 3 times.");
        }
    }

    keepDice(index) {
        if (index >= 0 && index < 5) { 
            if (this.diceToKeep[index] === false) {
                this.diceToKeep[index] = true; 
            } else {
                this.diceToKeep[index] = false; 
            }
        } else {
            console.log("Invalid dice index.");
        }
    }

    getGameState() {
        return {
            numberOfRoles: this.numberOfRoles,
            Values: this.Values,
            diceToKeep: this.diceToKeep
        };
    }
}
