package rhythmwheels.soundcategories;

import rhythmwheels.BassDrum;
import rhythmwheels.Clap;
import rhythmwheels.HiHat;
import rhythmwheels.Hup;
import rhythmwheels.Rest;
import rhythmwheels.Scratch1;
import rhythmwheels.Scratch2;
import rhythmwheels.Scratch3;
import rhythmwheels.Sound;
import rhythmwheels.Tube;

public class HipHop extends SoundCategory
{
    public HipHop()
    {
        numSounds = 9;
        catName = "HipHop";

        sounds = new Sound[numSounds];
        sounds[0] = new Rest();
        sounds[1] = new Scratch1();
        sounds[2] = new Scratch2();
        sounds[3] = new Scratch3();
        sounds[4] = new Hup();
        sounds[5] = new Clap();
        sounds[6] = new Tube();
        sounds[7] = new BassDrum();
        sounds[8] = new HiHat();

        names = new String[numSounds];
        names[0] = "Rest";
        names[1] = "Scratch 1";
        names[2] = "Scratch 2";
        names[3] = "Scratch 3";
        names[4] = "Hup";
        names[5] = "Clap";
        names[6] = "Tube";
        names[7] = "Bass Drum";
        names[8] = "Hi Hat";
    }
}