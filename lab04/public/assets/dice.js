/*  James Billinger
    300187686
    June 10th, 2024
    CSI3140[Z]
*/

console.log('dice.js hello world');


class Dice {
    constructor(sides = 6) {
        this.sides = sides;
    }

    roll() {

        return Math.floor(this.sides * Math.random()) + 1;
    }
}

