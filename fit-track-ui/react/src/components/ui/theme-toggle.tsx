"use client";

import { Moon, Sun } from "lucide-react";
import { useTheme } from "../../context/theme-context";

import { Button } from "./button.tsx";
import {
    DropdownMenu,
    DropdownMenuContent,
    DropdownMenuItem,
    DropdownMenuTrigger,
} from "./dropdown-menu.tsx";

export function ThemeToggle() {
    const { setTheme } = useTheme();

    return (
        <DropdownMenu>
            <DropdownMenuTrigger asChild>
                <Button 
                    variant="outline" 
                    size="icon"
                    className="border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-800 text-gray-700 dark:text-gray-200 hover:bg-gray-50 dark:hover:bg-gray-700"
                >
                    <Sun className="h-[1.2rem] w-[1.2rem] rotate-0 scale-100 transition-all dark:-rotate-90 dark:scale-0" />
                    <Moon className="absolute h-[1.2rem] w-[1.2rem] rotate-90 scale-0 transition-all dark:rotate-0 dark:scale-100" />
                    <span className="sr-only">Toggle theme</span>
                </Button>
            </DropdownMenuTrigger>
            <DropdownMenuContent 
                align="end"
                className="bg-white dark:bg-gray-800 border-gray-200 dark:border-gray-700"
            >
                <DropdownMenuItem 
                    onClick={() => setTheme("light")}
                    className="text-gray-700 dark:text-gray-200 hover:bg-gray-100 dark:hover:bg-gray-700"
                >
                    Light
                </DropdownMenuItem>
                <DropdownMenuItem 
                    onClick={() => setTheme("dark")}
                    className="text-gray-700 dark:text-gray-200 hover:bg-gray-100 dark:hover:bg-gray-700"
                >
                    Dark
                </DropdownMenuItem>
                <DropdownMenuItem 
                    onClick={() => setTheme("system")}
                    className="text-gray-700 dark:text-gray-200 hover:bg-gray-100 dark:hover:bg-gray-700"
                >
                    System
                </DropdownMenuItem>
            </DropdownMenuContent>
        </DropdownMenu>
    );
} 