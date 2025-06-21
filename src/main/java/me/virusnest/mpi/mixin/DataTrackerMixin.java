package me.virusnest.mpi.mixin;

import net.minecraft.entity.data.DataTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(DataTracker.class)
public interface DataTrackerMixin {
    @Accessor("entries")
    DataTracker.Entry<?>[] entries();
}

