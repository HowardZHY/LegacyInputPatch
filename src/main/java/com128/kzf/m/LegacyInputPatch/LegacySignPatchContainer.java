package com128.kzf.m.LegacyInputPatch;

import net.fabricmc.api.ModInitializer;

public class LegacySignPatchContainer implements ModInitializer {
	@Override
	public void onInitialize() {
		System.out.println("Signs can contain multi-byte characters on Serverside now.");
	}
}
