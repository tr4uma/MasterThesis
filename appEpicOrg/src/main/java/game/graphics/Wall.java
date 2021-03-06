package game.graphics;

import android.content.Context;

import game.physics.CollisionBox;
import game.physics.Square;
import sfogl.integration.Node;
import shadow.math.SFVertex3f;

/**
 * Rappresenta un muro a forma di parallelepipedo.
 * @author Stefano De Pace
 *
 */
public class Wall implements MazeObject{

    private Square box;
    private int textureID;

    /**
     * Crea un nuovo muro, di dimensioni proporzionali a quelle dello Square dato.
     * @param box Square che rappresenta la scatola di collisione del Wall, così come
     *            le sue dimensioni e posizione.
     * @param textureID Indice nelle risorse della texture che rappresenta la faccia di un Wall
     *                  di dimensioni unitarie; viene ripetuta proporzionalmente alle dimensioni.
     */
    public Wall(Square box, int textureID){
        this.box=box;
        this.textureID=textureID;
    }

    @Override
    public Node getNode(Context context){
        Node node = new Node();
        node.setModel(new WallGenerator(context,ShadersKeeper.getProgram(ShadersKeeper.STANDARD_TEXTURE_SHADER),
                textureID, box.getxSize(), box.getySize(), box.getzSize()).getModel());
        SFVertex3f position=box.getPos();
        node.getRelativeTransform().setPosition(position.getX(),position.getY(),position.getZ());
        return node;
    }

    @Override
    public CollisionBox getBox(){
        return box;
    }

}
