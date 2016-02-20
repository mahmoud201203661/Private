<?php

require('pusher-http-php-master/lib/Pusher.php');


$pusher = new Pusher('YOUR_APP_KEY', 'YOUR APP SECRET', 'YOUR APP ID');

$text = $_POST['message'];
echo '$text';
$data['message'] = $text;

$pusher->trigger('notifications', 'new_notification', $data);


// trigger on my_channel' an event called 'my_event' with this payload:

$data['message'] = 'hello world';
$pusher->trigger('notifications', 'new_notification', $data);
// you can run the built-in PHP web server using the following command:
// `php -S localhost:8000`

?>