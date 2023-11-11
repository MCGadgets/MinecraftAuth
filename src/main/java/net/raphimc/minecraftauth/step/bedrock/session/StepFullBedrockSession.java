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
package net.raphimc.minecraftauth.step.bedrock.session;

import com.google.gson.JsonObject;
import lombok.EqualsAndHashCode;
import lombok.Value;
import net.raphimc.minecraftauth.step.AbstractStep;
import net.raphimc.minecraftauth.step.SameInputOptionalMergeStep;
import net.raphimc.minecraftauth.step.bedrock.StepMCChain;
import net.raphimc.minecraftauth.step.bedrock.StepPlayFabToken;
import net.raphimc.minecraftauth.step.xbl.StepXblXstsToken;
import org.apache.http.client.HttpClient;

public class StepFullBedrockSession extends SameInputOptionalMergeStep<StepMCChain.MCChain, StepPlayFabToken.PlayFabToken, StepXblXstsToken.XblXsts<?>, StepFullBedrockSession.FullBedrockSession> {

    public StepFullBedrockSession(final AbstractStep<StepXblXstsToken.XblXsts<?>, StepMCChain.MCChain> prevStep1, final AbstractStep<StepXblXstsToken.XblXsts<?>, StepPlayFabToken.PlayFabToken> prevStep2) {
        super(prevStep1, prevStep2);
    }

    @Override
    public FullBedrockSession applyStep(final HttpClient httpClient, final StepMCChain.MCChain mcChain, final StepPlayFabToken.PlayFabToken playFabToken) throws Exception {
        return new FullBedrockSession(mcChain, playFabToken);
    }

    @Override
    public FullBedrockSession fromDeduplicatedJson(final JsonObject json) {
        final StepMCChain.MCChain mcChain = this.prevStep.fromJson(json.getAsJsonObject("mcChain"));
        final StepPlayFabToken.PlayFabToken playFabToken = this.prevStep2.fromJson(json.getAsJsonObject("playFabToken"));
        return new FullBedrockSession(mcChain, playFabToken);
    }

    @Value
    @EqualsAndHashCode(callSuper = false)
    public static class FullBedrockSession extends SameInputOptionalMergeStep.StepResult<StepMCChain.MCChain, StepPlayFabToken.PlayFabToken> {

        StepMCChain.MCChain mcChain;
        StepPlayFabToken.PlayFabToken playFabToken;

        @Override
        protected StepMCChain.MCChain prevResult() {
            return this.mcChain;
        }

        @Override
        protected StepPlayFabToken.PlayFabToken prevResult2() {
            return this.playFabToken;
        }

        @Override
        protected JsonObject _toJson() {
            final JsonObject json = new JsonObject();
            if (this.mcChain != null) json.add("mcChain", this.mcChain.toJson());
            if (this.playFabToken != null) json.add("playFabToken", this.playFabToken.toJson());
            return json;
        }

    }

}