package net.minecraft.client.gui;

import club.bluezenith.module.modules.render.hud.HUD;
import club.bluezenith.util.client.ServerUtils;
import club.bluezenith.util.math.RandomUtil;
import club.bluezenith.util.render.ColorUtil;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.List;

public class GuiNewChat extends Gui {
	private static final Logger logger = LogManager.getLogger();
	private final Minecraft mc;
	private final List<String> sentMessages = Lists.newArrayList();
	private final List<ChatLine> chatLines = Lists.newArrayList();
	private final List<ChatLine> field_146253_i = Lists.newArrayList();
	private int scrollPos;
	private boolean isScrolled;
	private FontRenderer font;

	public GuiNewChat(Minecraft mcIn) {
		this.mc = mcIn;
		this.font = mc.fontRendererObj;
	}

	public void drawChat(int p_146230_1_) {
		font = mc.fontRendererObj;
		if (this.mc.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN) {
			int i = this.getLineCount();
			boolean flag = false;
			int j = 0;
			int k = this.field_146253_i.size();
			float f = this.mc.gameSettings.chatOpacity * 0.9F + 0.1F;

			if (k > 0) {
				if (this.getChatOpen()) {
					flag = true;
				}

				float f1 = this.getChatScale();
				int l = MathHelper.ceiling_float_int((float) this.getChatWidth() / f1);
				GlStateManager.pushMatrix();
				GlStateManager.translate(2.0F, 20.0F, 0.0F);
				GlStateManager.scale(f1, f1, 1.0F);

				for (int i1 = 0; i1 + this.scrollPos < this.field_146253_i.size() && i1 < i; ++i1) {
					ChatLine chatline = this.field_146253_i.get(i1 + this.scrollPos);

					if (chatline != null) {
						int j1 = p_146230_1_ - chatline.getUpdatedCounter();

						if (j1 < 200 || flag) {
							double d0 = (double) j1 / 200.0D;
							d0 = 1.0D - d0;
							d0 = d0 * 10.0D;
							d0 = MathHelper.clamp(d0, 0.0D, 1.0D);
							d0 = d0 * d0;
							int l1 = (int) (255.0D * d0);

							if (flag) {
								l1 = 255;
							}

							l1 = (int) ((float) l1 * f);
							++j;

							if (l1 > 3) {
								int i2 = 0;
								int j2 = (-i1 * font.FONT_HEIGHT) - (mc.currentScreen instanceof GuiChat ? 0 : HUD.getChatOffset());
								//RenderUtil.blur(i2 + 2.0F, j2 - font.FONT_HEIGHT + 20.0F + 461.5f, i2 + l + 4 + 2.0F, j2 + 20.0F + 461.5f);
								if (!HUD.elements.getOptionState("Transparent Chat"))
									drawRect(i2, j2 - font.FONT_HEIGHT, i2 + l + 4, j2, l1 / 2 << 24);
								String s = chatline.getChatComponent().getFormattedText();

								GlStateManager.enableBlend();
								font.drawStringWithShadow(s, (float) i2, j2 - font.FONT_HEIGHT, 16777215 + (l1 << 24));
								GlStateManager.resetColor();
								GlStateManager.disableAlpha();
								GlStateManager.disableBlend();
							}
						}
					}
				}

				if (flag) {
					int k2 = font.FONT_HEIGHT;
					GlStateManager.translate(-3.0F, 0.0F, 0.0F);
					int l2 = k * k2 + k;
					int i3 = j * k2 + j;
					int j3 = this.scrollPos * i3 / k;
					int k1 = i3 * i3 / l2;

					if (l2 != i3) {
						int k3 = j3 > 0 ? 170 : 96;
						int l3 = this.isScrolled ? 13382451 : 3355562;
						drawRect(0, -j3, 2, -j3 - k1, l3 + (k3 << 24));
						drawRect(2, -j3, 1, -j3 - k1, 13421772 + (k3 << 24));
					}
				}

				GlStateManager.popMatrix();
			}
		}
	}

	/**
	 * Clears the chat.
	 */
	public void clearChatMessages() {
		this.field_146253_i.clear();
		this.chatLines.clear();
		this.sentMessages.clear();
	}

	public void printChatMessage(IChatComponent p_146227_1_) {
		this.printChatMessageWithOptionalDeletion(p_146227_1_, 0);
	}

	/**
	 * prints the ChatComponent to Chat. If the ID is not 0, deletes an existing Chat Line of that ID from the GUI
	 */
	public void printChatMessageWithOptionalDeletion(IChatComponent p_146234_1_, int p_146234_2_) {
		this.setChatLine(p_146234_1_, p_146234_2_, this.mc.ingameGUI.getUpdateCounter(), false);
		final String text = p_146234_1_.getUnformattedText();
		if(!(text.contains("$") && text.contains("{") && text.contains("}")))
			logger.info("[CHAT] " + p_146234_1_.getUnformattedText());
	}

	private void setChatLine(IChatComponent chatComponent, int p_146237_2_, int p_146237_3_, boolean p_146237_4_) {
		if (p_146237_2_ != 0) {
			this.deleteChatLine(p_146237_2_);
		}

		String s = chatComponent.getFormattedText();

		final String[] poopClients = { "Rise", "Tenacity", "Astolfo", "Diablo", "Azura", "novoline.wtf", "Blue Zenith" };

		final String stripped = ColorUtil.stripFormatting(s),
				client = RandomUtil.select(poopClients);

		if(stripped.startsWith("A player has been removed") && ServerUtils.hypixel) {
			s = s.replace("A player", "A " + client + " user");
		}

		if(stripped.startsWith("Watchdog has banned") && stripped.contains("players")) {
			s = s.replace("players", client + " users");
		}
		if(stripped.startsWith("Staff have banned an additional"))
			s = s.replace("in the last 7 days", client + " users in the last 7 days");

		chatComponent = new ChatComponentText(s);

		int i = MathHelper.floor_float((float) this.getChatWidth() / this.getChatScale());
		List<IChatComponent> list = GuiUtilRenderComponents.wrapComponent(chatComponent, i, font, false, false);
		boolean flag = this.getChatOpen();

		for (IChatComponent ichatcomponent : list) {
			if (flag && this.scrollPos > 0) {
				this.isScrolled = true;
				this.scroll(1);
			}

			this.field_146253_i.add(0, new ChatLine(p_146237_3_, ichatcomponent, p_146237_2_));
		}

		while (this.field_146253_i.size() > 1000) { //100 before
			this.field_146253_i.remove(this.field_146253_i.size() - 1);
		}

		if (!p_146237_4_) {
			this.chatLines.add(0, new ChatLine(p_146237_3_, chatComponent, p_146237_2_));

			while (this.chatLines.size() > 1000) { //100 before
				this.chatLines.remove(this.chatLines.size() - 1);
			}
		}
	}

	public void refreshChat() {
		this.field_146253_i.clear();
		this.resetScroll();

		for (int i = this.chatLines.size() - 1; i >= 0; --i) {
			ChatLine chatline = this.chatLines.get(i);
			this.setChatLine(chatline.getChatComponent(), chatline.getChatLineID(), chatline.getUpdatedCounter(), true);
		}
	}

	public List<String> getSentMessages() {
		return this.sentMessages;
	}

	/**
	 * Adds this string to the list of sent messages, for recall using the up/down arrow keys
	 */
	public void addToSentMessages(String p_146239_1_) {
		if (this.sentMessages.isEmpty() || !this.sentMessages.get(this.sentMessages.size() - 1).equals(p_146239_1_)) {
			this.sentMessages.add(p_146239_1_);
		}
	}

	/**
	 * Resets the chat scroll (executed when the GUI is closed, among others)
	 */
	public void resetScroll() {
		this.scrollPos = 0;
		this.isScrolled = false;
	}

	/**
	 * Scrolls the chat by the given number of lines.
	 */
	public void scroll(int p_146229_1_) {
		this.scrollPos += p_146229_1_;
		int i = this.field_146253_i.size();

		if (this.scrollPos > i - this.getLineCount()) {
			this.scrollPos = i - this.getLineCount();
		}

		if (this.scrollPos <= 0) {
			this.scrollPos = 0;
			this.isScrolled = false;
		}
	}

	/**
	 * Gets the chat component under the mouse
	 */
	public IChatComponent getChatComponent(int mouseX, int mouseY) {
		if (this.getChatOpen()) {
			ScaledResolution scaledresolution = new ScaledResolution(this.mc);
			int i = scaledresolution.getScaleFactor();
			float f = this.getChatScale();
			int j = mouseX / i - 3;
			int k = mouseY / i - 27;
			j = MathHelper.floor_float((float) j / f);
			k = MathHelper.floor_float((float) k / f);

			if (j >= 0 && k >= 0) {
				int l = Math.min(this.getLineCount(), this.field_146253_i.size());

				if (j <= MathHelper.floor_float((float) this.getChatWidth() / this.getChatScale()) && k < font.FONT_HEIGHT * l + l) {
					int i1 = k / font.FONT_HEIGHT + this.scrollPos;

					if (i1 >= 0 && i1 < this.field_146253_i.size()) {
						ChatLine chatline = this.field_146253_i.get(i1);
						int j1 = 0;

						for (IChatComponent ichatcomponent : chatline.getChatComponent()) {
							if (ichatcomponent instanceof ChatComponentText) {
								j1 += font.getStringWidth(GuiUtilRenderComponents.removeChatColors(((ChatComponentText) ichatcomponent).getChatComponentText_TextValue(), false));

								if (j1 > j) {
									return ichatcomponent;
								}
							}
						}
					}

				}
			}
		}
		return null;
	}

	/**
	 * Returns true if the chat GUI is open
	 */
	public boolean getChatOpen() {
		return this.mc.currentScreen instanceof GuiChat;
	}

	/**
	 * finds and deletes a Chat line by ID
	 */
	public void deleteChatLine(int p_146242_1_) {
		Iterator<ChatLine> iterator = this.field_146253_i.iterator();

		while (iterator.hasNext()) {
			ChatLine chatline = iterator.next();

			if (chatline.getChatLineID() == p_146242_1_) {
				iterator.remove();
			}
		}

		iterator = this.chatLines.iterator();

		while (iterator.hasNext()) {
			ChatLine chatline1 = iterator.next();

			if (chatline1.getChatLineID() == p_146242_1_) {
				iterator.remove();
				break;
			}
		}
	}

	public int getChatWidth() {
		return calculateChatboxWidth(this.mc.gameSettings.chatWidth);
	}

	public int getChatHeight() {
		return calculateChatboxHeight(this.getChatOpen() ? this.mc.gameSettings.chatHeightFocused : this.mc.gameSettings.chatHeightUnfocused);
	}

	/**
	 * Returns the chatscale from mc.gameSettings.chatScale
	 */
	public float getChatScale() {
		return this.mc.gameSettings.chatScale;
	}

	public static int calculateChatboxWidth(float p_146233_0_) {
		int i = 320;
		int j = 40;
		return MathHelper.floor_float(p_146233_0_ * (float) (i - j) + (float) j);
	}

	public static int calculateChatboxHeight(float p_146243_0_) {
		int i = 180;
		int j = 20;
		return MathHelper.floor_float(p_146243_0_ * (float) (i - j) + (float) j);
	}

	public int getLineCount() {
		return this.getChatHeight() / font.FONT_HEIGHT;
	}
}
