/*
 * This file is part of MinecraftAuth - https://github.com/RaphiMC/MinecraftAuth
 * Copyright (C) 2023 RK_01/RaphiMC and contributors
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.raphimc.minecraftauth.step.xbl.adapter;

import com.google.gson.JsonObject;
import net.raphimc.minecraftauth.step.AbstractStep;
import net.raphimc.minecraftauth.step.xbl.StepXblXstsToken;
import net.raphimc.minecraftauth.step.xbl.session.StepFullXblSession;
import org.apache.http.client.HttpClient;

public class StepXblXstsToFullXblSession extends AbstractStep<StepXblXstsToken.XblXsts<?>, StepFullXblSession.FullXblSession> {

    public StepXblXstsToFullXblSession(final AbstractStep<?, StepXblXstsToken.XblXsts<?>> prevStep) {
        super(prevStep.name, prevStep);
    }

    @Override
    public StepFullXblSession.FullXblSession applyStep(final HttpClient httpClient, final StepXblXstsToken.XblXsts<?> prevResult) throws Exception {
        return new FullXblSessionWrapper(prevResult);
    }

    @Override
    public StepFullXblSession.FullXblSession fromJson(final JsonObject json) {
        return new FullXblSessionWrapper(this.prevStep.fromJson(json));
    }

    @Override
    public JsonObject toJson(final StepFullXblSession.FullXblSession result) {
        final FullXblSessionWrapper fullXblSessionWrapper = (FullXblSessionWrapper) result;

        return this.prevStep.toJson(fullXblSessionWrapper.xblXsts);
    }

    private static class FullXblSessionWrapper extends StepFullXblSession.FullXblSession {

        private final StepXblXstsToken.XblXsts<?> xblXsts;

        public FullXblSessionWrapper(final StepXblXstsToken.XblXsts<?> xblXsts) {
            super(xblXsts.getFullXblSession());

            this.xblXsts = xblXsts;
        }

    }

}
