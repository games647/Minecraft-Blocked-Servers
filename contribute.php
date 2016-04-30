<?php

echo hashServer("example.minecraft.com");

function hashServer($serverAddress) {
    return sha1($serverAddress);
}
