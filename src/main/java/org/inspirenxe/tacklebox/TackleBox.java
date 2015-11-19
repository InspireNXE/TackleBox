/*
 * This file is part of TackleBox, licensed under the MIT License (MIT).
 *
 * Copyright (c) InspireNXE <http://github.com/InspireNXE/>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.inspirenxe.tacklebox;

import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.action.FishingEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;

import java.util.Optional;

@Plugin(id = "tacklebox", name = "TackleBox")
public class TackleBox {
    private static final Text MESSAGE = Texts.of("You reeled in ");

    @Listener
    public void onFishingStop(FishingEvent.Stop event) {
        final Optional<Player> player = event.getCause().first(Player.class);
        if (!player.isPresent()) {
            return;
        }

        final Transaction<ItemStackSnapshot> transaction = event.getItemStackTransaction();
        if (transaction.getFinal().getType().equals(ItemTypes.NONE)) {
            return;
        }

        player.get().sendMessage(MESSAGE, format(transaction.getFinal().getType().getTranslation().get()));
    }

    private Text format(String plain) {
        if (!plain.endsWith("s")) {
            if (startsWithAny(plain, "a", "e", "i", "o", "u")) {
                plain = "an " + plain;
            } else {
                plain = "a " + plain;
            }
        }
        return Texts.of(plain);
    }

    private boolean startsWithAny(String text, String... values) {
        for (String value : values) {
            if (text.startsWith(value)) {
                return true;
            }
        }
        return false;
    }
}
