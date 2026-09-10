@echo off
title Sail Lanka - Live Real-Time Activity Monitor
color 0b
echo ========================================================================
echo   Starting Sail Lanka Real-Time Transaction Monitor...
echo ========================================================================
powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0watch_live_updates.ps1"
pause
