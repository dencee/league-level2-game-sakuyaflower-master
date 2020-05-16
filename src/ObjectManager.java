import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class ObjectManager implements ActionListener {
    PlayerBoy boy;
    ArrayList<Fruits> fruits = new ArrayList<Fruits>();
    Random random = new Random();
    int score = 0;
    
    //
    // next fruit in the String array GamePanel.fruitOrder
    //
    int nextFruit = 0;
    ArrayList<Fruits> caughtFruits = new ArrayList<Fruits>();

    public int getScore() {
        return this.score;
    }
    
    //
    // Used in the game panel to get the next fruit
    //
    public int getNextFruit() {
        return nextFruit;
    }

    ObjectManager(PlayerBoy boy) {
        this.boy = boy;
        addFruits( random.nextInt( Puzzle.WIDTH - 100 ), "apple" );
    }

    void addFruits(int x, String fruitType) {
        int width = 0;
        int height = 0;
        if( fruitType.equals( "Apple" ) ) {
            height = 100;
            width = 100;
        } else if( fruitType.equals( "ElderBerry" ) ) {
            height = 100;
            width = 100;
        } else if( fruitType.equals( "Honeydew" ) ) {
            height = 100;
            width = 100;
        } else if( fruitType.equals( "Pear" ) ) {
            height = 100;
            width = 100;
        } else if( fruitType.equals( "Pineapple" ) ) {
            height = 100;
            width = 100;
        } else if( fruitType.equals( "Strawberry" ) ) {
            height = 100;
            width = 100;
        } else if( fruitType.equals( "Yellow Nectarine" ) ) {
            height = 100;
            width = 100;
        } else if( fruitType.equals( "Tomato" ) ) {
            height = 100;
            width = 100;
        } else if( fruitType.equals( "Lemon" ) ) {
            height = 100;
            width = 100;
        }
        fruits.add( new Fruits( fruitType, ( x - width ), width, height ) );
    }

    void update() {
        boy.update();
        for( int i = 0; i < fruits.size(); i++ ) {
            fruits.get( i ).update();
            if( fruits.get( i ).y >= Puzzle.HEIGHT ) {
                fruits.get( i ).isActive = false;
            }
        }
        checkCollision();
        purgeObjects();

    }

    void draw(Graphics g) {
        boy.draw( g );
        for( int i = 0; i < fruits.size(); i++ ) {
            fruits.get( i ).draw( g );

        }
    }

    void checkCollision() {
        for( int i = 0; i < fruits.size(); i++ ) {
            if( boy.collisionBox.intersects( fruits.get( i ).collisionBox ) ) {
                String expectedFruit = GamePanel.fruitOrder[ nextFruit ];
                String caughtFruit = fruits.get( i ).fruitType;
                
                System.out.println( "Caught fruit: " + caughtFruit + " expected: " + expectedFruit );
                
                //
                // Check if we caught the correct fruit
                //
                if( caughtFruit.equals( expectedFruit ) ) {
                    caughtFruits.add( fruits.get( i ) );
                    score++;
                    nextFruit++;
                    
                    //
                    // If we caught all the fruits
                    //
                    if( caughtFruits.size() == GamePanel.fruitOrder.length ) {
                        // win
                    }
                    
                } else {
                    //
                    // We caught the wrong fruit
                    //
                    score--;
                    nextFruit = 0;
                }
                
                fruits.remove( i );
            }
        }
    }

    void purgeObjects() {
        for( int i = 0; i < fruits.size(); i++ ) {
            if( fruits.get( i ).isActive == false ) {
                fruits.remove( i );
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        addFruits( new Random().nextInt( Puzzle.WIDTH - 100 ) + 100, Fruits.getRandomFruitType() );
    }
}