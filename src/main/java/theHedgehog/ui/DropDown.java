// package theHedgehog.ui;
//
// import basemod.IUIElement;
// import basemod.ModPanel;
// import com.badlogic.gdx.graphics.Color;
// import com.badlogic.gdx.graphics.g2d.SpriteBatch;
// import com.megacrit.cardcrawl.core.CardCrawlGame;
// import com.megacrit.cardcrawl.core.Settings;
// import com.megacrit.cardcrawl.helpers.FontHelper;
// import com.megacrit.cardcrawl.helpers.Label;
// import com.megacrit.cardcrawl.localization.UIStrings;
// import com.megacrit.cardcrawl.screens.options.DropdownMenu;
// import com.megacrit.cardcrawl.screens.options.DropdownMenuListener;
// import theHedgehog.SonicMod;
//
// import java.util.ArrayList;
// import java.util.List;
// import java.util.function.Consumer;
//
//
// public class DropDown implements DropdownMenuListener, IUIElement
// {
//    // static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(SonicMod..FLAG_EXPLANATION.ID);
//    private Consumer<Integer> integerConsumer;
//    private DropdownMenu dropdownMenu;
//    private Label label;
//    private float x;
//    private float y;
//    private float w;
//    private float h;
//    private ModPanel parent;
//    public String selection;
//
//    public DropDown(List<String> options, float xPos, float yPos, ModPanel parent, Label label, Consumer<DropDown> flagDropDownConsumer, Consumer<Integer> integerConsumer)
//    {
//        this.x = xPos;
//        this.y = yPos;
//        this.parent = parent;
//        this.dropdownMenu = new DropdownMenu(this, (ArrayList<String>) options, FontHelper.buttonLabelFont, Color.CORAL);
//        this.label = label;
//        this.integerConsumer = integerConsumer;
//
//        this.dropdownMenu.setSelectedIndex(SonicMod.getIndex());
//    }
//
//    @Override
//    public void changedSelectionTo(DropdownMenu dropdownMenu, int i, String s)
//    {
//        selection = s.toLowerCase();
//        integerConsumer.accept(i);
//    }
//
//    @Override
//    public void render(SpriteBatch spriteBatch) {
//        label.render(spriteBatch);
//        dropdownMenu.render(spriteBatch, x, y);
//    }
//
//    @Override
//    public void update() {
//        dropdownMenu.update();
//    }
//
//    @Override
//    public int renderLayer() {
//        return 0;
//    }
//
//    @Override
//    public int updateOrder() {
//        return 0;
//    }
//
//    @Override
//    public void set(float xPos, float yPos) {
//        this.x = xPos;
//        this.y = yPos;
//    }
// }