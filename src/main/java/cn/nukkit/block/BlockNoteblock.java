package cn.nukkit.block;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemTool;
import cn.nukkit.math.Vector3;
import net.pocketdreams.sequinland.level.event.block.NoteblockEvent;
import net.pocketdreams.sequinland.level.sound.NoteSound;

/**
 * Created by Snake1999 on 2016/1/17.
 * Package cn.nukkit.block in project nukkit.
 */
public class BlockNoteblock extends BlockSolid {

    public BlockNoteblock() {
        this(0);
    }

    public BlockNoteblock(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return "Note Block";
    }

    @Override
    public int getId() {
        return NOTEBLOCK;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_AXE;
    }

    @Override
    public double getHardness() {
        return 0.8D;
    }

    @Override
    public double getResistance() {
        return 4D;
    }

    public boolean canBeActivated() {
        return true;
    }

    public int getStrength() {
        if (this.meta < 24) this.meta++;
        else this.meta = 0;
        this.getLevel().setBlock(this, this);
        return this.meta;
    }

    public int getInstrument() {
        Block below = this.getSide(Vector3.SIDE_DOWN);
        switch (below.getId()) {
            case WOODEN_PLANK:
            case NOTEBLOCK:
            case CRAFTING_TABLE:
                return NoteblockEvent.INSTRUMENT_BASS;
            case SAND:
            case SANDSTONE:
            case SOUL_SAND:
                return NoteblockEvent.INSTRUMENT_TABOUR;
            case GLASS:
            case GLASS_PANEL:
            case GLOWSTONE_BLOCK:
                return NoteblockEvent.INSTRUMENT_CLICK;
            case COAL_ORE:
            case DIAMOND_ORE:
            case EMERALD_ORE:
            case GLOWING_REDSTONE_ORE:
            case GOLD_ORE:
            case IRON_ORE:
            case LAPIS_ORE:
            case REDSTONE_ORE:
                return NoteblockEvent.INSTRUMENT_BASS_DRUM;
            default:
                return NoteblockEvent.INSTRUMENT_PIANO;
        }
    }

    public boolean onActivate(Item item) {
        return this.onActivate(item, null);
    }

    public boolean onActivate(Item item, Player player) {
        Block up = this.getSide(Vector3.SIDE_UP);
        if (up.getId() == Block.AIR) {
            this.getLevel().addEvent(new NoteblockEvent(this, this.getInstrument(), this.getStrength()));
            this.getLevel().addSound(new NoteSound(this, this.getStrength()));
            return true;
        } else {
            return false;
        }
    }
}
