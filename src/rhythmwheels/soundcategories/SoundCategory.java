package rhythmwheels.soundcategories;

import java.util.ArrayList;
import java.util.List;
import rhythmwheels.Sound;

public class SoundCategory {

    protected Sound[] sounds;
    protected String[] names;
    protected String catName;
    int numSounds = 8;
    public static List<SoundCategory> installeCcategories =
            new ArrayList<SoundCategory>();

    public SoundCategory(Sound[] m_sounds, String[] m_names, String m_catName,
            int m_numSounds) {
        sounds = m_sounds;
        names = m_names;
        catName = m_catName;
        numSounds = m_numSounds;
    }

    /**
     *
     * @return
     */
    public Sound[] getSounds() {
        return sounds;
    }

    /**
     *
     * @return
     */
    public int getNumSounds() {
        return numSounds;
    }

    /**
     *
     * @return
     */
    public String[] getNames() {
        return names;
    }

    public void addSound(String newName, Sound newSound) {
        //initialize replacement arrays
        int newNumSounds = this.numSounds + 1;
        Sound[] newSoundList = new Sound[newNumSounds];
        String[] newNameList = new String[newNumSounds];

        //store values in old arrays into new arrays
        for (int i = 0; i < numSounds; i++) {
            newSoundList[i] = this.sounds[i];
            newNameList[i] = this.names[i];
        }

        //store the new sound file and name into the respective arrays
        newSoundList[ newSoundList.length - 1] = newSound;
        newNameList[ newNameList.length - 1] = newName;

        //replace all data files necessary
        this.sounds = newSoundList;
        this.names = newNameList;
        this.numSounds = newNumSounds;
    }

    @Override
    public String toString() {
        return catName;
    }
}
