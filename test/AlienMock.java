import main.java.de.tum.in.ase.eist.Dimension2D;
import main.java.de.tum.in.ase.eist.Model.AlienSpaceship;
import main.java.de.tum.in.ase.eist.Model.BulletCollision;
import main.java.de.tum.in.ase.eist.Point2D;
import org.easymock.EasyMockExtension;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(EasyMockExtension.class)
public class AlienMock {
    @TestSubject
    private AlienSpaceship alienSpaceship=new AlienSpaceship(new Point2D(20,30),new Dimension2D(50,50));

    @Mock
    private BulletCollision bulletCollisionMock = createNiceMock(BulletCollision.class);

    @Test
     void destroyTest(){
        AlienSpaceship alienSpaceship=new AlienSpaceship(new Point2D(20,30),new Dimension2D(50,50));
        expect(bulletCollisionMock.detectCollision()).andReturn(true);
        replay(bulletCollisionMock);
        alienSpaceship.destroy(bulletCollisionMock);
        assertTrue(alienSpaceship.isHit());
    }

}
