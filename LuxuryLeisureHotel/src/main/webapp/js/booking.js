/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


document.addEventListener("DOMContentLoaded", function() {
    const today = new Date();
    const tomorrow = new Date(today);
    tomorrow.setDate(tomorrow.getDate() + 1);

    const todayStr = today.toISOString().split('T')[0];
    const tomorrowStr = tomorrow.toISOString().split('T')[0];

    const checkInInput = document.getElementById('checkIn');
    const checkOutInput = document.getElementById('checkOut');

    checkInInput.setAttribute('min', todayStr);
    checkOutInput.setAttribute('min', todayStr);

    checkInInput.value = todayStr;
    checkOutInput.value = tomorrowStr;

    checkInInput.addEventListener('change', function() {
        const checkInDate = new Date(checkInInput.value);
        checkInDate.setDate(checkInDate.getDate() + 1);
        checkOutInput.setAttribute('min', checkInDate.toISOString().split('T')[0]);
    });

    document.getElementById('reservationForm').addEventListener('submit', function(event) {
        if (new Date(checkInInput.value) >= new Date(checkOutInput.value)) {
            alert('Check-out date must be after the check-in date.');
            event.preventDefault();
        }
    });
    
    checkInInput.addEventListener('change', calculateTotalAmount);
    checkOutInput.addEventListener('change', calculateTotalAmount);
    document.getElementsByName('roomtypeId').forEach(function(elem) {
        elem.addEventListener('change', calculateTotalAmount);
    });

    function calculateTotalAmount() {
        const checkInDate = new Date(checkInInput.value);
        const checkOutDate = new Date(checkOutInput.value);
        const timeDiff = Math.abs(checkOutDate.getTime() - checkInDate.getTime());
        const diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24)); 

        const roomRate = parseFloat(document.querySelector('input[name="roomtypeId"]:checked').getAttribute('data-rate') || 0);
        const totalAmount = diffDays * roomRate;

        document.getElementById('totalAmount').innerText = totalAmount.toFixed(2);
    }
});

document.addEventListener('DOMContentLoaded', function() {
    const today = new Date();
    const tomorrow = new Date(today);
    tomorrow.setDate(tomorrow.getDate() + 1);

    const todayStr = today.toISOString().split('T')[0];
    const tomorrowStr = tomorrow.toISOString().split('T')[0];

    const checkInInput = document.getElementById('checkIn');
    const checkOutInput = document.getElementById('checkOut');

    checkInInput.setAttribute('min', todayStr);
    checkOutInput.setAttribute('min', todayStr);

    checkInInput.value = todayStr;
    checkOutInput.value = tomorrowStr;

    checkInInput.addEventListener('change', function() {
        const checkInDate = new Date(checkInInput.value);
        checkInDate.setDate(checkInDate.getDate() + 1);
        checkOutInput.setAttribute('min', checkInDate.toISOString().split('T')[0]);
    });

    document.getElementById('reservationForm').addEventListener('submit', function(event) {
        if (new Date(checkInInput.value) >= new Date(checkOutInput.value)) {
            alert('Check-out date must be after the check-in date.');
            event.preventDefault();
        }
    });
});